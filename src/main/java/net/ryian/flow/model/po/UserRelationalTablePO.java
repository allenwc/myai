package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author allenwc
 * @date 2024/5/9 09:06
 */
@TableName("tb_user_relational_table")
public class UserRelationalTablePO {

    @TableId(type = IdType.AUTO)
    Long id;
    String tid;
    String db;
    Long user;
    String name;
    String info;
    String status;
    String schemaInfo;
    Long createTime;
    Long updateTime;

}
