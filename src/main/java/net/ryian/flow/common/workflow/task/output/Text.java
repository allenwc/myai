package net.ryian.flow.common.workflow.task.output;

import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/15 09:08
 */
@Component
public class Text {

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        Object text = workflow.getNodeFieldValue(nodeId, "text");
        workflow.getNodeFieldValue(nodeId, "output_title");
        workflow.updateNodeFieldValue(nodeId, "text", text);
        workflow.updateNodeFieldValue(nodeId, "output", text);
        return workflow.getData();
    }

}
