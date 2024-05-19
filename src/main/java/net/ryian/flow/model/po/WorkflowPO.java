package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/4 09:16
 */
@TableName("tb_workflow")
@Data
public class WorkflowPO {

    @TableId(type = IdType.AUTO)
    Long id;
    String wid;
    Long user;
    /**
     * 工作流状态
     * @see net.ryian.flow.common.enums.WorkflowStatus
     */
    String status;
    String title;
    String brief;
    String images;
    String tags;
    String data;
    String language;
    Boolean fastAccess;
    Long createTime;
    Long updateTime;
    Long expireTime;

}
