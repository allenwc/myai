package net.ryian.flow.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.ryian.flow.mapper.DatabaseMapper;
import net.ryian.flow.model.po.UserVectorDatabasePO;
import net.ryian.flow.service.DatabaseService;

/**
 * @author allenwc
 * @date 2024/5/3 23:55
 */
@Service
public class DatabaseSericeImpl extends ServiceImpl<DatabaseMapper, UserVectorDatabasePO> implements DatabaseService {
}
