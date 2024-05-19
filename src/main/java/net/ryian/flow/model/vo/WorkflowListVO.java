package net.ryian.flow.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 12:05
 */
@Data
public class WorkflowListVO extends PaginateVO {

    List<WorkflowVO> workflows;

    @JsonProperty("fast_access_workflows")
    List<WorkflowVO> fastAccessWorkflows;

}
