package net.ryian.flow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import net.ryian.flow.common.response.ResponseEnum;
import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.model.vo.WorkflowTemplateVO;
import net.ryian.flow.model.vo.param.WorkflowTemplateGetParam;
import net.ryian.flow.service.WorkflowTemplateService;

/**
 * @author allenwc
 * @date 2024/5/4 08:32
 */
@RestController
public class OfficialSiteController {

    WorkflowTemplateService workflowTemplateService;

    @Autowired
    public OfficialSiteController(WorkflowTemplateService workflowTemplateService) {
        this.workflowTemplateService = workflowTemplateService;
    }

    @PostMapping("/api/official_site__list_tags")
    public ServerResponseEntity<List<Object>> listTags() {
        return ServerResponseEntity.success(Lists.newArrayList());
    }

    @PostMapping("/api/official_site__list_templates")
    public ServerResponseEntity<Map<String,Object>> listTemplates() {
        Map<String,Object> data = Map.of(
            "templates", workflowTemplateService.listOfficialTemplates(),
            "total",0,
            "page_size",10,
            "page",1
        );
        return ServerResponseEntity.success(data);
    }

    @PostMapping("/api/official_site__get_template")
    public ServerResponseEntity<WorkflowTemplateVO> getTemplates(@RequestBody WorkflowTemplateGetParam param) {
        return ServerResponseEntity.success(workflowTemplateService.getTemplate(param.getTid()));
    }

    @PostMapping("api/official_site__get_update_info")
    public ServerResponseEntity<Void> getUpdateInfo() {
        ServerResponseEntity serverResponseEntity = new ServerResponseEntity();
        serverResponseEntity.setStatus(ResponseEnum.NOT_READY.value());
        return serverResponseEntity;
    }

}
