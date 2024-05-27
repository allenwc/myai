package net.ryian.flow.common.workflow.task.control_flows;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/26 10:15
 */
@Component
public class RandomChoice {

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        List<?> inputList = (List<?>) workflow.getNodeFieldValue(nodeId, "input");
        Object output;
        Random random = new Random();
        if (inputList.get(0) instanceof List) {
            output = ((List<?>) inputList.get(0)).get(random.nextInt(((List<?>) inputList.get(0)).size()));
        } else {
            output = inputList.get(random.nextInt(inputList.size()));
        }
        workflow.updateNodeFieldValue(nodeId, "output", output);
        return workflow.getData();
    }

}
