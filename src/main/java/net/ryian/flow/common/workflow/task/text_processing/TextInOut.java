package net.ryian.flow.common.workflow.task.text_processing;

import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/14 20:15
 */
@Component
public class TextInOut {

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        String text = (String)workflow.getNodeFieldValue(nodeId, "text");
        workflow.updateNodeFieldValue(nodeId, "output", text);
        return workflow.getData();
    }

}
