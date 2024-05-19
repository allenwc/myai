package net.ryian.flow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.ryian.flow.common.enums.WorkflowTemplateStatus;
import net.ryian.flow.mapper.WorkflowTemplateMapper;
import net.ryian.flow.model.po.WorkflowTemplatePO;
import net.ryian.flow.model.vo.WorkflowTemplateVO;
import net.ryian.flow.model.vo.WorkflowTemplateVOConvertor;
import net.ryian.flow.service.WorkflowTemplateService;

/**
 * @author allenwc
 * @date 2024/5/12 20:38
 */
@Service
public class WorkflowTemplateServiceImpl extends ServiceImpl<WorkflowTemplateMapper, WorkflowTemplatePO> implements WorkflowTemplateService {

    @Override
    public List<WorkflowTemplateVO> listOfficialTemplates() {
        QueryWrapper<WorkflowTemplatePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_official", 1);
        queryWrapper.eq("status", WorkflowTemplateStatus.VALID.getValue());
        return WorkflowTemplateVOConvertor.INSTANCE.toVOList(list(queryWrapper));
    }

    @Override
    public WorkflowTemplateVO getTemplate(String tid) {
        QueryWrapper<WorkflowTemplatePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", tid);
        return WorkflowTemplateVOConvertor.INSTANCE.toVO(getOne(queryWrapper));
    }
}
