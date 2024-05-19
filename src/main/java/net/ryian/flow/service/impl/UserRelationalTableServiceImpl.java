package net.ryian.flow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.ryian.flow.mapper.UserRelationalTableMapper;
import net.ryian.flow.model.po.UserRelationalTablePO;
import net.ryian.flow.model.vo.param.RelationalDatabaseTableListParam;
import net.ryian.flow.service.UserRelationalTableService;

/**
 * @author allenwc
 * @date 2024/5/9 09:09
 */
@Service
public class UserRelationalTableServiceImpl extends ServiceImpl<UserRelationalTableMapper, UserRelationalTablePO> implements
    UserRelationalTableService {

    @Override
    public List<UserRelationalTablePO> listByParam(RelationalDatabaseTableListParam param) {
        QueryWrapper<UserRelationalTablePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("db", param.getRid());
        return this.list(queryWrapper);
    }
}
