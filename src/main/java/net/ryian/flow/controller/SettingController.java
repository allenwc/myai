package net.ryian.flow.controller;

import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.model.vo.UserSettingVO;
import net.ryian.flow.model.vo.UserSettingVOConvertor;
import net.ryian.flow.model.vo.param.SettingParam;
import net.ryian.flow.service.UserSettingService;

/**
 * @author allenwc
 * @date 2024/5/15 21:11
 */
@RestController
public class SettingController {

    UserSettingService userSettingService;

    @Autowired
    public SettingController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    @PostMapping("/api/setting__get")
    public ServerResponseEntity<UserSettingVO> getSetting() {
        return ServerResponseEntity.success(UserSettingVOConvertor.INSTANCE.toVO(userSettingService.get()));
    }

    @PostMapping("/api/setting__update")
    public ServerResponseEntity<Void> update(@RequestBody SettingParam param) {
        userSettingService.updateSetting(param);
        return ServerResponseEntity.success();
    }

}
