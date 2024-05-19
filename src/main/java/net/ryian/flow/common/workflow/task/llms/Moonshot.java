package net.ryian.flow.common.workflow.task.llms;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.integration.ServiceFactory;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/15 09:09
 */
@Component
public class Moonshot {

    private final ServiceFactory serviceFactory;

    @Autowired
    public Moonshot(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        String text = (String) workflow.getNodeFieldValue(nodeId, "prompt");
        String model = (String) workflow.getNodeFieldValue(nodeId, "llm_model");
        float temperature = (float) workflow.getNodeFieldValue(nodeId, "temperature");
        text = serviceFactory.getMoonshotChatClient().call(
                new Prompt(text, OpenAiChatOptions.builder().withModel(model).withTemperature(temperature).build()))
            .getResult().getOutput().getContent();
        workflow.updateNodeFieldValue(nodeId, "output", text);
        return workflow.getData();
    }

}
