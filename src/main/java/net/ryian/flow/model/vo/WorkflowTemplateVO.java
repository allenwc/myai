package net.ryian.flow.model.vo;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 09:16
 */
@Data
public class WorkflowTemplateVO {

    String id;
    Long user;
    /**
     * 工作流状态
     * @see net.ryian.flow.common.enums.WorkflowTemplateStatus
     */
    String status;
    String title;
    String brief;
    List images;
    String tags;
    JsonNode data;
    boolean isOfficial;
    Long createTime;
    Long updateTime;

}
