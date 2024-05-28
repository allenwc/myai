package net.ryian.flow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import net.ryian.flow.common.utils.JsonUtil;
import net.ryian.flow.integration.ServiceFactory;
import net.ryian.flow.mapper.UserSettingMapper;
import net.ryian.flow.model.bo.Setting;
import net.ryian.flow.model.po.UserSettingPO;
import net.ryian.flow.model.vo.param.SettingParam;
import net.ryian.flow.service.UserSettingService;

/**
 * @author allenwc
 * @date 2024/5/15 21:10
 */
@Service
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSettingPO>
    implements UserSettingService {

    private final ServiceFactory serviceFactory;

    @Autowired
    public UserSettingServiceImpl(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @Override
    public void updateSetting(SettingParam param) {
        UserSettingPO userSettingPO = this.get();
        if (userSettingPO == null) {
            userSettingPO = new UserSettingPO();
            userSettingPO.setData(JsonUtil.jsonToString(param.getData()));
            userSettingPO.setCreateTime(System.currentTimeMillis());
            this.save(userSettingPO);
        } else {
            userSettingPO.setData(JsonUtil.jsonToString(param.getData()));
            userSettingPO.setUpdateTime(System.currentTimeMillis());
            this.updateById(userSettingPO);
        }
        notifySetting();
    }

    @PostConstruct
    @SneakyThrows
    public void notifySetting() {
        UserSettingPO userSetting = this.get();
        if (userSetting != null) {
            Setting setting = new ObjectMapper().readValue(userSetting.getData(), Setting.class);
            serviceFactory.updateSetting(setting);
        }
    }

    @Override
    public UserSettingPO get() {
        List<UserSettingPO> list = this.list();
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
