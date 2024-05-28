package net.ryian.flow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.model.vo.WorkflowRunRecordVO;
import net.ryian.flow.model.vo.WorkflowRunRecordVOConvertor;
import net.ryian.flow.service.WorkflowRunRecordService;

/**
 * @author allenwc
 * @date 2024/5/4 15:01
 */
@RestController
public class WorkflowRunRecordController {

    private final WorkflowRunRecordService workflowRunRecordService;

    @Autowired
    public WorkflowRunRecordController(WorkflowRunRecordService workflowRunRecordService) {
        this.workflowRunRecordService = workflowRunRecordService;
    }

    @PostMapping("/api/workflow_run_record__list")
    public ServerResponseEntity<List<WorkflowRunRecordVO>> list() {
        return ServerResponseEntity.success(WorkflowRunRecordVOConvertor.INSTANCE.toVOList(workflowRunRecordService.myList()));
    }

    @PostMapping("/api/workflow_run_record__get")
    public ServerResponseEntity<WorkflowRunRecordVO> get(@RequestBody Map<String,String> param) {
        String rid = param.get("rid");
        return ServerResponseEntity.success(WorkflowRunRecordVOConvertor.INSTANCE.toVO(workflowRunRecordService.getByRid(rid)));
    }

}
