package net.ryian.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;

import net.ryian.flow.model.po.WorkflowPO;
import net.ryian.flow.model.vo.WorkflowListVO;
import net.ryian.flow.model.vo.WorkflowVO;
import net.ryian.flow.model.vo.param.WorkflowListParam;

/**
 * @author allenwc
 * @date 2024/5/4 09:28
 */
public interface WorkflowService extends IService<WorkflowPO> {

    WorkflowVO create(WorkflowVO workflowVO);

    WorkflowListVO list(WorkflowListParam param);

    boolean removeByWid(String wid);

    boolean updateByWid(WorkflowPO workflowPO);

    boolean updateFavorite(String wid,boolean favorite);

    WorkflowVO getByWid(String wid);

    String run(String wid,String data);

    WorkflowVO getByRid(String rid);

}
