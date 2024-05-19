package net.ryian.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.ryian.flow.model.po.UserSettingPO;
import net.ryian.flow.model.vo.param.SettingParam;

/**
 * @author allenwc
 * @date 2024/5/15 21:09
 */
public interface UserSettingService extends IService<UserSettingPO> {

    void updateSetting(SettingParam param);

    UserSettingPO get();

}
