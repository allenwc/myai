package net.ryian.flow.common.enums;

/**
 * @author allenwc
 * @date 2024/5/5 09:04
 */
public enum WorkflowStatus {

    INVALID("INVALID","未开始"),
    EXPIRED("EXPIRED","已过期"),
    DELETED("DELETED","已删除"),
    VALID("VALID","有效"),
    ;

    private final String value;

    private final String desc;

    WorkflowStatus(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

}
