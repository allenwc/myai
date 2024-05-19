package net.ryian.flow.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.ryian.flow.mapper.RelationalDatabaseMapper;
import net.ryian.flow.model.po.UserRelationalDatabasePO;
import net.ryian.flow.service.RelationalDatabaseService;

/**
 * @author allenwc
 * @date 2024/5/3 23:55
 */
@Service
public class RelationalDatabaseSericeImpl extends ServiceImpl<RelationalDatabaseMapper, UserRelationalDatabasePO> implements
    RelationalDatabaseService {
}
