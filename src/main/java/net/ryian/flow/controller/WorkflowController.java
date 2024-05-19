package net.ryian.flow.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.enums.WorkflowRunRecordStatus;
import net.ryian.flow.common.response.ResponseEnum;
import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.common.utils.JsonUtil;
import net.ryian.flow.model.po.WorkflowPO;
import net.ryian.flow.model.po.WorkflowRunRecordPO;
import net.ryian.flow.model.vo.WorkflowListVO;
import net.ryian.flow.model.vo.WorkflowVO;
import net.ryian.flow.model.vo.WorkflowVOConvertor;
import net.ryian.flow.model.vo.param.WorkflowGetParam;
import net.ryian.flow.model.vo.param.WorkflowListParam;
import net.ryian.flow.service.WorkflowRunRecordService;
import net.ryian.flow.service.WorkflowService;

/**
 * @author allenwc
 * @date 2024/5/3 10:55
 */
@RestController
public class WorkflowController {

    private final WorkflowService workflowService;
    private final WorkflowRunRecordService workflowRunRecordService;

    @Autowired
    public WorkflowController(WorkflowService workflowService, WorkflowRunRecordService workflowRunRecordService) {
        this.workflowService = workflowService;
        this.workflowRunRecordService = workflowRunRecordService;
    }

    @PostMapping("/api/workflow__create")
    public ServerResponseEntity<WorkflowVO> create(@RequestBody WorkflowVO workflowVO) {
        return ServerResponseEntity.success(workflowService.create(workflowVO));
    }

    @PostMapping("/api/workflow__update")
    public ServerResponseEntity<WorkflowVO> update(@RequestBody WorkflowVO workflowVO) {
        WorkflowPO workflowPO = WorkflowVOConvertor.INSTANCE.toPO(workflowVO);
        workflowService.updateByWid(workflowPO);
        return ServerResponseEntity.success(workflowVO);
    }

    @PostMapping("/api/workflow__get")
    public ServerResponseEntity<WorkflowVO> get(@RequestBody WorkflowGetParam param) {
        WorkflowVO workflowVO = workflowService.getByWid(param.getWid());
        if(workflowVO == null) {
            workflowVO = new WorkflowVO();
            workflowVO.setWid(param.getWid());
        }
        return ServerResponseEntity.success(workflowVO);
    }

    @PostMapping("/api/workflow__list")
    public ServerResponseEntity<WorkflowListVO> list(@RequestBody WorkflowListParam param) {
        return ServerResponseEntity.success(workflowService.list(param));
    }

    @PostMapping("/api/workflow__delete")
    public ServerResponseEntity<Void> delete(@RequestBody WorkflowVO workflowVO) {
        workflowService.removeByWid(workflowVO.getWid());
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/workflow__add_to_fast_access")
    public ServerResponseEntity<Void> addToFastAccess(@RequestBody WorkflowVO workflowVO) {
        workflowService.updateFavorite(workflowVO.getWid(),true);
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/workflow__delete_from_fast_access")
    public ServerResponseEntity<Void> deleteFromFastAccess(@RequestBody WorkflowVO workflowVO) {
        workflowService.updateFavorite(workflowVO.getWid(),false);
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/workflow_tag__list")
    public ServerResponseEntity<List<Object>> listTags() {
        return ServerResponseEntity.success(Lists.newArrayList());
    }

    @PostMapping("/api/workflow__run")
    public ServerResponseEntity<Map<String,Object>> run(@RequestBody WorkflowVO workflowVO) {
        String rid = workflowService.run(workflowVO.getWid(), JsonUtil.jsonToString(workflowVO.getData()));
        return ServerResponseEntity.success(Map.of("rid",rid));
    }

    @PostMapping("/api/workflow__check_status")
    public ServerResponseEntity<WorkflowVO> checkStatus(@RequestBody Map<String,String> param) {
        String rid = param.get("rid");
        WorkflowRunRecordPO workflowRunRecordPO = workflowRunRecordService.getByRid(rid);
        if(WorkflowRunRecordStatus.FINISHED.getValue().equals(workflowRunRecordPO.getStatus())) {
            WorkflowVO workflowVO = workflowService.getByWid(workflowRunRecordPO.getWid());
            workflowVO.setData(JsonUtil.stringToJson(workflowRunRecordPO.getData()));
            return ServerResponseEntity.success(workflowVO,workflowRunRecordPO.getStatus());
        } else {
            ServerResponseEntity<WorkflowVO> serverResponseEntity = new ServerResponseEntity<>();
            serverResponseEntity.setStatus(ResponseEnum.NOT_READY.value());
            serverResponseEntity.setMsg(workflowRunRecordPO.getStatus());
            return serverResponseEntity;
        }
    }

}
