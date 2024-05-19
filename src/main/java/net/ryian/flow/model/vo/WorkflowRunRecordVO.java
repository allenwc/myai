package net.ryian.flow.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 19:22
 */
@Data
public class WorkflowRunRecordVO {

    String rid;
    String status;
    JsonNode data;
    boolean shared;
    @JsonProperty("is_public")
    boolean isPublic;
    @JsonProperty("start_time")
    long startTime;
    @JsonProperty("end_time")
    long endTime;

}
