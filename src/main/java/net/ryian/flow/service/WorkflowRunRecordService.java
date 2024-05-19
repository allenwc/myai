package net.ryian.flow.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.ryian.flow.model.po.WorkflowRunRecordPO;

/**
 * @author allenwc
 * @date 2024/5/4 17:56
 */
public interface WorkflowRunRecordService extends IService<WorkflowRunRecordPO> {

    String startRun(String wid,String data);

    String finishRun(String rid,String data);

    WorkflowRunRecordPO getByRid(String rid);

    List<WorkflowRunRecordPO> myList();

}
