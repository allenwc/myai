package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import net.ryian.flow.common.enums.DatabaseStatus;

/**
 * @author allenwc
 * @date 2024/5/3 23:45
 */
@Data
@TableName("tb_user_vector_database")
public class UserVectorDatabasePO {

    @TableId(type = IdType.AUTO)
    Long id;
    String vid;
    Long user;
    String name;
    /**
     * 数据库状态
     * @see DatabaseStatus
     */
    String status;
    String info;
    Integer embeddingSize;
    String embeddingModel;
    Long createTime;
    Long updateTime;
    Long expireTime;

}
