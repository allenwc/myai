package net.ryian.flow.common.workflow.task.llms;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/26 14:23
 */
@Data
@Builder
public class ModelOutput {

    private String contentOutput;
    private List<String> toolCalls;
    private Map<String, String> functionCallArguments;
    private int promptTokens;
    private int completionTokens;

}
