package net.ryian.flow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author allenwc
 * @date 2024/5/15 21:07
 */
@TableName("tb_user_setting")
@Data
public class UserSettingPO {

    @TableId(type = IdType.AUTO)
    Long id;
    Long user;
    String data;
    Long createTime;
    Long updateTime;

}
