package net.ryian.flow.common.workflow.task.llms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import net.ryian.flow.common.workflow.Workflow;
import net.ryian.flow.common.workflow.task.TaskParams;

/**
 * @author allenwc
 * @date 2024/5/26 13:58
 */
@Component
public abstract class BaseLlm {

    @Autowired
    protected TaskExecutor taskExecutor;

    private Semaphore semaphore = new Semaphore(1);

    public Object execute(TaskParams params) {
        Workflow workflow = new Workflow(params.getWorkflowData());
        String nodeId = params.getNodeId();
        Object inputPrompt = workflow.getNodeFieldValue(nodeId, "prompt");
        String model = (String) workflow.getNodeFieldValue(nodeId, "llm_model");
        BigDecimal temperature = (BigDecimal) workflow.getNodeFieldValue(nodeId, "temperature");


        List<String> prompts = new ArrayList<>();
        if(inputPrompt instanceof List) {
            prompts = (List<String>) inputPrompt;
        } else {
            prompts.add((String) inputPrompt);
        }

        Map<CompletableFuture<ModelOutput>,Integer> futures = new HashMap();
        for (int i = 0; i < prompts.size(); i++) {
            String prompt = prompts.get(i);
            int index = i;
            ModelInput modelInput = ModelInput.builder().prompt(prompt).model(model).temperature(temperature.floatValue()).build();
            CompletableFuture<ModelOutput> future = CompletableFuture.supplyAsync(() -> {
                try {
                    semaphore.acquire();
                    return processPrompt(modelInput, index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                return null;
            }, taskExecutor);
            futures.put(future,index);
        }

        String[] contentOutputs = new String[prompts.size()];
        for (CompletableFuture<ModelOutput> future : futures.keySet()) {
            int i = futures.get(future);
            try {
                ModelOutput result = future.get();
                contentOutputs[i] = result.getContentOutput();
            } catch (Exception e) {
                System.err.println("Generated an exception: " + e);
                System.err.println("Prompt: " + prompts.get(i));
            }
        }

        if(!(inputPrompt instanceof List<?>)) {
            workflow.updateNodeFieldValue(nodeId, "output", contentOutputs[0]);
        } else {
            workflow.updateNodeFieldValue(nodeId, "output", contentOutputs);
        }
        return workflow.getData();
    }

    abstract ModelOutput processPrompt(ModelInput modelInput, int index);

}
