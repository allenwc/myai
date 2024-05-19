package net.ryian.flow.common.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

/**
 * @author allenwc
 * @date 2024/5/5 10:40
 */
public class Workflow {

    private JSONObject workflowData;

    private JSONObject originalWorkflowData;

    private JSONObject relatedWorkflows;

    private JSONArray edges;

    private Map<String, String> nodeIdMap;

    private Map<String, Node> nodes;

    private Map<String, Node> workflowInvokeNodes;

    private DAG dag;

    private String workflowId;

    private String recordId;

    public Workflow(String workflowDataString) {
        this(JSON.parseObject(workflowDataString));
    }

    public Workflow(JSONObject workflowData) {
        this.workflowData = workflowData;
        if (!workflowData.containsKey("original_workflow_data")) {
            this.originalWorkflowData = new JSONObject();
            this.workflowData.put("original_workflow_data", this.originalWorkflowData);
        } else {
            this.originalWorkflowData = workflowData.getJSONObject("original_workflow_data");
        }
        this.relatedWorkflows = workflowData.getJSONObject("related_workflows");
        this.edges = this.workflowData.getJSONArray("edges");
        parseNodes();
        this.dag = createDag();
        // The methods parseNodes(), handleWorkflowInvoke(), getRelatedSubnodes(), addSubnodesAndSubedges(), addSubnode(), updateSubnodeFields(), createDag(), updateEdgeNodes(), getOriginalNode(), addIsolatedNodesToDag(), getSortedTaskOrder(), getFieldActualNode(), updateOriginalWorkflowData(), cleanWorkflowData(), getNode(), getFieldValue(), updateNodeFieldValue(), getNodeFields(), reportWorkflowStatus(), setNodeStatus() and getData() are not included in this translation because they are complex and would make this response too long. They would need to be translated separately.
    }

    private DAG createDag() {
        DAG dag = new DAG();
        for (Object edge : this.edges) {
            JSONObject edgeObj = (JSONObject) edge;
            edgeObj = updateEdgeNodes(edgeObj);
            String start = edgeObj.getString("source");
            String end = edgeObj.getString("target");
            dag.addEdge(start, end);
        }
        this.addIsolatedNodesToDag(dag);
        return dag;
    }

    private JSONObject updateEdgeNodes(JSONObject edge) {
        String source = edge.getString("source");
        String target = edge.getString("target");

        if (this.workflowInvokeNodes.containsKey(source)) {
            Node workflowInvokeNode = this.workflowInvokeNodes.get(source);
            Map<String, Object> originalSourceNodeField = workflowInvokeNode.getField((String) edge.get("sourceHandle"));
            String originalOutputFieldKey = originalSourceNodeField.containsKey("output_field_key") ?
                (String) originalSourceNodeField.get("output_field_key") :
                (String) originalSourceNodeField.get("fieldName");
            source = getOriginalNode(source, (String) edge.get("sourceHandle"));
            edge.put("source", this.nodeIdMap.get(source + workflowInvokeNode.getId()));
            edge.put("sourceHandle", originalOutputFieldKey);
        }

        if (this.workflowInvokeNodes.containsKey(target)) {
            Node workflowInvokeNode = this.workflowInvokeNodes.get(target);
            target = getOriginalNode(target, (String) edge.get("targetHandle"));
            edge.put("target", this.nodeIdMap.get(target + workflowInvokeNode.getId()));
        }
        return edge;
    }

    private String getOriginalNode(String node, String handle) {
        Node workflowInvokeNode = this.workflowInvokeNodes.get(node);
        Map<String, Object> field = workflowInvokeNode.getField(handle);
        return (String) field.get("node");
    }

    private void addIsolatedNodesToDag(DAG dag) {
        Set<String> allNodes = dag.getAllNodes();
        for (String nodeId: this.nodes.keySet()) {
            Node node = this.nodes.get(nodeId);
            if (!allNodes.contains(nodeId) && !node.getCategory().equals("triggers") && !node.getCategory()
                .equals("assistedNodes")) {
                dag.addNode(nodeId);
            }
        }
    }

    private void parseNodes() {
        JSONArray nodesList = this.workflowData.getJSONArray("nodes");
        Map<String, Node> nodes = new HashMap<>();
        Map<String, Node> workflowInvokeNodes = new HashMap<>();
        while (!nodesList.isEmpty()) {
            JSONObject node = (JSONObject) nodesList.remove(0);
            Node nodeObj = new Node(node);
            if (!nodeObj.getType().equals("WorkflowInvoke")) {
                nodes.put(nodeObj.getId(), nodeObj);
            } else {
                // For "WorkflowInvoke" nodes, you need to add the nodes and edges of the subworkflow to the current workflow
                //todo nodesList.addAll(newNodes);
                workflowInvokeNodes.put(nodeObj.getId(), nodeObj);
            }
        }

        // Update the nodes in the original data
        this.workflowData.put("nodes", nodes.values().stream().map(Node::getData).collect(Collectors.toList()));
        this.nodes = nodes;
        this.workflowInvokeNodes = workflowInvokeNodes;
    }

    public Map<String,Node> getNodes() {
        return nodes;
    }

    public List<JSONObject> getSortedTaskOrder() {
        List<String> nodesOrder = this.dag.topologicalSort();
        List<JSONObject> tasks = new ArrayList<>();
        for (String nodeId: nodesOrder) {
            Node node = this.nodes.get(nodeId);
            String taskName = node.getTaskName();
            JSONObject task = new JSONObject();
            task.put("node_id", nodeId);
            task.put("task_name", taskName);
            tasks.add(task);
        }
        return tasks;
    }

    public String getData() {
        return this.workflowData.toJSONString();
    }

    public Object getNodeFieldValue(String nodeId, String field) {
        return this.getNodeFieldValue(nodeId, field, null);
    }

    public Object getNodeFieldValue(String nodeId, String field, Object defaultValue) {
        Node node = this.getNodes().get(nodeId);
        if (node == null) {
            return defaultValue;
        }
        if(this.edges.isEmpty()) {
            return node.getField(field).getOrDefault("value", defaultValue);
        }
        for(Object edge: this.edges) {
            JSONObject edgeObj = (JSONObject) edge;
            Node sourceNode = this.nodes.get(edgeObj.getString("source"));
            if(sourceNode != null && (sourceNode.getType().equals("Empty") || sourceNode.getType().equals("ButtonTrigger"))) {
                continue;
            }
            if(edgeObj.getString("target").equals(nodeId) && edgeObj.getString("targetHandle").equals(field)) {
                String sourceNodeId = edgeObj.getString("source");
                Object value = this.getNodes().get(sourceNodeId).getField(edgeObj.getString("sourceHandle")).get("value");
                if(value == null) {
                    value = defaultValue;
                }
                this.updateNodeFieldValue(nodeId, field, value);
                return value;
            }
        }
        return node.getField(field).getOrDefault("value", defaultValue);
    }

    public void updateNodeFieldValue(String nodeId, String field, Object value) {
        Node node = this.getNodes().get(nodeId);
        JSONObject fieldData = node.getField(field);
        fieldData.put("value", value);
        node.updateField(field, fieldData);
    }

    public Set<String> getNodeFields(String nodeId) {
        Node node = this.getNodes().get(nodeId);
        JSONObject data = node.getData().getJSONObject("data");
        JSONObject template = data.getJSONObject("template");
        return template.keySet();
    }
}
