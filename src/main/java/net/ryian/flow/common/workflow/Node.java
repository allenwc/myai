package net.ryian.flow.common.workflow;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author allenwc
 * @date 2024/5/5 10:38
 */
public class Node {
    private JSONObject nodeData;
    private JSONObject fieldMap;

    public Node(JSONObject nodeData) {
        this.nodeData = nodeData;
        this.fieldMap = nodeData.getJSONObject("data").getJSONObject("template");
    }

    public JSONObject getField(String field) {
        JSONObject result = fieldMap.getJSONObject(field);
        if (result == null) {
            result = new JSONObject();
        }
        return result;
    }

    public void updateField(String field, JSONObject data) {
        fieldMap.put(field, data);
        nodeData.getJSONObject("data").put("template", fieldMap);
    }

    public int getStatus() {
        return nodeData.getJSONObject("data").getIntValue("status",0);
    }

    public void updateStatus(int status) {
        nodeData.getJSONObject("data").put("status", status);
    }

    public void updateCredits(int credits) {
        nodeData.getJSONObject("data").put("credits", credits);
    }

    public String getId() {
        return nodeData.getString("id");
    }

    public JSONObject getData() {
        return nodeData;
    }

    public String getTaskName() {
        return nodeData.getJSONObject("data").getString("task_name");
    }

    public String getType() {
        return nodeData.getString("type");
    }

    public String getCategory() {
        return nodeData.getString("category");
    }

    @Override
    public String toString() {
        return "<Node " + getType() + " @" + getId() + ">";
    }
}
