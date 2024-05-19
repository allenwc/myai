package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import net.ryian.flow.common.enums.DatabaseStatus;

/**
 * @author allenwc
 * @date 2024/5/4 08:44
 */
@Data
@TableName("tb_user_relational_database")
public class UserRelationalDatabasePO {

    @TableId(type = IdType.AUTO)
    Long id;
    String rid;
    Long user;
    String name;
    /**
     * 数据库状态
     * @see DatabaseStatus
     */
    String status;
    String info;
    Long createTime;
    Long updateTime;
    Long expireTime;

}
