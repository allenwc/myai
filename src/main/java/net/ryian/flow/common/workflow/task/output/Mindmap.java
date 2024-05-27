package net.ryian.flow.common.workflow.task.output;

import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/26 10:18
 */
@Component
public class Mindmap {

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        return workflow.getData();
    }

}
