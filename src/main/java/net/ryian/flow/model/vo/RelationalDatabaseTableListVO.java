package net.ryian.flow.model.vo;

import java.util.List;

import lombok.Data;
import net.ryian.flow.model.po.UserRelationalTablePO;

/**
 * @author allenwc
 * @date 2024/5/9 09:57
 */
@Data
public class RelationalDatabaseTableListVO {

    List<UserRelationalTablePO> tables;

}
