package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 15:05
 */
@TableName("tb_workflow_run_record")
@Data
public class WorkflowRunRecordPO {

    @TableId(type = IdType.AUTO)
    Long id;
    String rid;
    String wid;
    /**
     * 工作流运行状态
     * @see net.ryian.flow.common.enums.WorkflowRunRecordStatus
     */
    String status;
    String data;
    Long scheduleTime;
    Long startTime;
    Long endTime;


}
