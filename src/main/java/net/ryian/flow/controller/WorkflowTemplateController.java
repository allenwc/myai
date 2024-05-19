package net.ryian.flow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.response.ServerResponseEntity;

/**
 * @author allenwc
 * @date 2024/5/12 22:13
 */
@RestController
public class WorkflowTemplateController {

    @PostMapping("/api/workflow_template__list")
    public ServerResponseEntity<List<Object>> list() {
        return ServerResponseEntity.success(List.of());
    }

}
