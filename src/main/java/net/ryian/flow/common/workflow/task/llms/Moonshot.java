package net.ryian.flow.common.workflow.task.llms;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.task.TaskParams;
import net.ryian.flow.integration.ServiceFactory;

/**
 * @author allenwc
 * @date 2024/5/15 09:09
 */
@Component
public class Moonshot extends BaseLlm{

    private final ServiceFactory serviceFactory;

    @Autowired
    public Moonshot(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    ModelOutput processPrompt(ModelInput modelInput, int index) {
        String content = serviceFactory.getMoonshotChatClient().call(
                new Prompt(modelInput.getPrompt(), OpenAiChatOptions.builder().withModel(modelInput.getModel()).withTemperature(modelInput.getTemperature()).build()))
            .getResult().getOutput().getContent();
        return ModelOutput.builder().contentOutput(content).build();
    }

    @Override
    public Object execute(TaskParams params) {
        return super.execute(params);
    }
}
