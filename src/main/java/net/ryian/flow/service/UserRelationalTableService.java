package net.ryian.flow.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.ryian.flow.model.po.UserRelationalTablePO;
import net.ryian.flow.model.vo.param.RelationalDatabaseTableListParam;

/**
 * @author allenwc
 * @date 2024/5/9 09:09
 */
public interface UserRelationalTableService extends IService<UserRelationalTablePO> {

    List<UserRelationalTablePO> listByParam(RelationalDatabaseTableListParam param);

}
