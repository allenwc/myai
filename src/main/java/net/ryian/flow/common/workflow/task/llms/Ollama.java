package net.ryian.flow.common.workflow.task.llms;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.integration.ServiceFactory;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/14 20:19
 */
@Component
public class Ollama {

    ServiceFactory serviceFactory;

    @Autowired
    public Ollama(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public Object execute(TaskParams taskParams) {
        Workflow workflow = new Workflow(taskParams.getWorkflowData());
        String nodeId = taskParams.getNodeId();
        String text = (String) workflow.getNodeFieldValue(nodeId, "prompt");
        String model = (String) workflow.getNodeFieldValue(nodeId, "llm_model");
        float temperature = (float) workflow.getNodeFieldValue(nodeId, "temperature");
        OllamaOptions options = OllamaOptions.create();
        options.setModel(model);
        options.setTemperature(temperature);
        Prompt prompt = new Prompt(text,options);
        text = serviceFactory.getOllamaChatClient().call(prompt).getResult().getOutput().getContent();
        workflow.updateNodeFieldValue(nodeId, "output", text);
        return workflow.getData();
    }

}
