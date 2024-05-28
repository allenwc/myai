package net.ryian.flow.common.workflow.task.text_processing;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/26 10:09
 */
@Component
public class ListRender {

    @SneakyThrows
    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        List<String> list = (List<String>) workflow.getNodeFieldValue(nodeId, "list");
        String outputType = (String) workflow.getNodeFieldValue(nodeId, "output_type");
        String separator = (String) workflow.getNodeFieldValue(nodeId, "separator", "\n");
        separator = new String(separator.getBytes(), "UTF-8");
        if ("text".equals(outputType)) {
            String outputText = String.join(separator, list);
            workflow.updateNodeFieldValue(nodeId, "output", outputText);
        } else if ("list".equals(outputType)) {
            workflow.updateNodeFieldValue(nodeId, "output", list);
        }

        return workflow.getData();
    }

}
