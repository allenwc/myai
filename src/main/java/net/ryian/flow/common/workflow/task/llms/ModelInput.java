package net.ryian.flow.common.workflow.task.llms;

import lombok.Builder;
import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/26 14:44
 */
@Data
@Builder
public class ModelInput {

    String prompt;
    String model;
    float temperature;

}
