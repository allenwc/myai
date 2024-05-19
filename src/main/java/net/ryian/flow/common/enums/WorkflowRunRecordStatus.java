package net.ryian.flow.common.enums;

import lombok.Getter;

/**
 * @author allenwc
 * @date 2024/5/4 20:29
 */
@Getter
public enum WorkflowRunRecordStatus {

    NOT_STARTED("not_started","未开始"),
    QUEUED("queued","排队中"),
    RUNNING("running","运行中"),
    FINISHED("finished","已完成"),
    FAILED("failed","失败")
    ;

    private final String value;

    private final String desc;

    WorkflowRunRecordStatus(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }
}
