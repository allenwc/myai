package net.ryian.flow.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.response.ServerResponseEntity;

/**
 * @author allenwc
 * @date 2024/5/19 21:32
 */
@RestController
public class HealthController {

    @RequestMapping("/i/health")
    public ServerResponseEntity<Void> health() {
        return ServerResponseEntity.success();
    }

}
