package net.ryian.flow.common.workflow.task.llms;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.task.TaskParams;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/14 20:19
 */
@Component
public class Ollama extends BaseLlm {

    ServiceFactory serviceFactory;

    @Autowired
    public Ollama(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public Object execute(TaskParams taskParams) {
        return super.execute(taskParams);
    }

    @Override
    ModelOutput processPrompt(ModelInput modelInput, int index) {
        OllamaOptions options = OllamaOptions.create();
        options.setModel(modelInput.getModel());
        options.setTemperature(modelInput.getTemperature());
        String content = serviceFactory.getOllamaChatClient().call(
                new Prompt(modelInput.getPrompt(), options))
            .getResult().getOutput().getContent();
        return ModelOutput.builder().contentOutput(content).build();
    }

}
