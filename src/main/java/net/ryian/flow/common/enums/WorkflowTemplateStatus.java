package net.ryian.flow.common.enums;

import lombok.Getter;

/**
 * @author allenwc
 * @date 2024/5/12 21:00
 */
@Getter
public enum WorkflowTemplateStatus {

    INVALID("INVALID","未开始"),
    DELETED("DELETED","已删除"),
    VALID("VALID","有效"),
    ;

    private final String value;

    private final String desc;

    WorkflowTemplateStatus(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

}
