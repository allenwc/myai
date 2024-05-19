package net.ryian.flow.common.enums;

import lombok.Getter;

/**
 * @author allenwc
 * @date 2024/5/5 08:44
 */
@Getter
public enum DatabaseStatus {

    INVALID("INVALID","无效"),
    EXPIRED("EXPIRED","已过期"),
    DELETING("DELETING","删除中"),
    DELETED("DELETED","已删除"),
    VALID("VALID","有效"),
    ERROR("ERROR","错误"),
    CREATING("CREATING","创建中"),
    ;

    private final String value;

    private final String desc;

    DatabaseStatus(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

}
