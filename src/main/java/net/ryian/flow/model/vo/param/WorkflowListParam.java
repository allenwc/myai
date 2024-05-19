package net.ryian.flow.model.vo.param;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 12:02
 */
@Data
public class WorkflowListParam {

    @JsonProperty("need_fast_access")
    boolean needFastAccess;

    int page;

    @JsonProperty("page_size")
    int pageSize;

    String tags;

    @JsonProperty("search_text")
    String searchText;

    @JsonProperty("workflow_related")
    String workflowRelated;

}
