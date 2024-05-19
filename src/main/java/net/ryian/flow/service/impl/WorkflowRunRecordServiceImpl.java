package net.ryian.flow.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.ryian.flow.common.enums.WorkflowRunRecordStatus;
import net.ryian.flow.mapper.WorkflowRunRecordMapper;
import net.ryian.flow.model.po.WorkflowRunRecordPO;
import net.ryian.flow.service.WorkflowRunRecordService;

/**
 * @author allenwc
 * @date 2024/5/4 17:56
 */
@Service
public class WorkflowRunRecordServiceImpl extends ServiceImpl<WorkflowRunRecordMapper, WorkflowRunRecordPO> implements
    WorkflowRunRecordService {

    @Override
    public String startRun(String wid,String data) {
        WorkflowRunRecordPO record = new WorkflowRunRecordPO();
        record.setWid(wid);
        record.setData(data);
        String rid = UUID.randomUUID().toString().replaceAll("-", "");
        record.setRid(rid);
        record.setStartTime(System.currentTimeMillis());
        record.setStatus(WorkflowRunRecordStatus.RUNNING.getValue());
        this.save(record);
        return rid;
    }

    @Override
    public String finishRun(String rid,String data) {
        WorkflowRunRecordPO record = this.getByRid(rid);
        record.setData(data);
        record.setStatus(WorkflowRunRecordStatus.FINISHED.getValue());
        record.setEndTime(System.currentTimeMillis());
        this.updateById(record);
        return record.getRid();
    }

    @Override
    public WorkflowRunRecordPO getByRid(String rid) {
        QueryWrapper<WorkflowRunRecordPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<WorkflowRunRecordPO> myList() {
        QueryWrapper<WorkflowRunRecordPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("start_time");
        return this.list(queryWrapper);
    }
}
