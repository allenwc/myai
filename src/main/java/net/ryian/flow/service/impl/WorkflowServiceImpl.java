package net.ryian.flow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;

import net.ryian.flow.mapper.WorkflowMapper;
import net.ryian.flow.model.po.WorkflowPO;
import net.ryian.flow.model.vo.WorkflowListVO;
import net.ryian.flow.model.vo.WorkflowVO;
import net.ryian.flow.model.vo.WorkflowVOConvertor;
import net.ryian.flow.model.vo.param.WorkflowListParam;
import net.ryian.flow.service.WorkflowEngineService;
import net.ryian.flow.service.WorkflowRunRecordService;
import net.ryian.flow.service.WorkflowService;

/**
 * @author allenwc
 * @date 2024/5/4 09:29
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, WorkflowPO> implements WorkflowService {

    private final WorkflowRunRecordService workflowRunRecordService;
    private final WorkflowEngineService workflowEngineService;

    @Autowired
    public WorkflowServiceImpl(WorkflowRunRecordService workflowRunRecordService,WorkflowEngineService workflowEngineService) {
        this.workflowRunRecordService = workflowRunRecordService;
        this.workflowEngineService = workflowEngineService;
    }

    @Override
    public WorkflowVO create(WorkflowVO workflowVO) {
        workflowVO.fill();
        this.save(WorkflowVOConvertor.INSTANCE.toPO(workflowVO));
        return workflowVO;
    }



    @Override
    public WorkflowListVO list(WorkflowListParam param) {
        WorkflowListVO workflowListVO = new WorkflowListVO();

        Page<WorkflowPO> page = new Page<>(param.getPage(), param.getPageSize() == 0 ? 10 : param.getPageSize());
        if(StringUtils.isNullOrEmpty(param.getSearchText())) {
            this.page(page);
        } else {
            QueryWrapper<WorkflowPO> wrapper = new QueryWrapper<>();
            wrapper.like("title", param.getSearchText());
            this.page(page, wrapper);
        }
        workflowListVO.setTotal(page.getTotal());
        workflowListVO.setPage(page.getCurrent());
        workflowListVO.setWorkflows(WorkflowVOConvertor.INSTANCE.toVOList(page.getRecords()));
        if(param.isNeedFastAccess()) {
            workflowListVO.setFastAccessWorkflows(
                WorkflowVOConvertor.INSTANCE.toVOList(
                this.list(new QueryWrapper<WorkflowPO>().eq("fast_access", true))));
        } else {
            workflowListVO.setFastAccessWorkflows(List.of());
        }
        return workflowListVO;
    }

    @Override
    public boolean removeByWid(String wid) {
        return this.remove(new QueryWrapper<WorkflowPO>().eq("wid", wid));
    }

    @Override
    public boolean updateByWid(WorkflowPO workflowPO) {
        UpdateWrapper<WorkflowPO> wrapper = new UpdateWrapper<>();
        wrapper.eq("wid", workflowPO.getWid());
        workflowPO.setUpdateTime(System.currentTimeMillis());
        boolean result = this.update(workflowPO, wrapper);
        if (!result) {
            workflowPO.setCreateTime(System.currentTimeMillis());
            result = this.save(workflowPO);
        }
        return result;
    }

    @Override
    public boolean updateFavorite(String wid,boolean favorite) {
        UpdateWrapper<WorkflowPO> wrapper = new UpdateWrapper<>();
        wrapper.set("fast_access", favorite);
        wrapper.set("update_time", System.currentTimeMillis());
        wrapper.eq("wid", wid);
        return this.update(wrapper);
    }

    @Override
    public WorkflowVO getByWid(String wid) {
        return WorkflowVOConvertor.INSTANCE.toVO(this.getOne(new QueryWrapper<WorkflowPO>().eq("wid", wid)));
    }

    @Override
    public String run(String wid,String data) {
        String rid = workflowRunRecordService.startRun(wid,data);
        workflowEngineService.run(rid);
        return rid;

    }

    @Override
    public WorkflowVO getByRid(String rid) {
        String wid = workflowRunRecordService.getByRid(rid).getWid();
        return this.getByWid(wid);
    }

}
