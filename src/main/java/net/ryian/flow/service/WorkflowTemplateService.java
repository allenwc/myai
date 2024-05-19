package net.ryian.flow.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.ryian.flow.model.po.WorkflowTemplatePO;
import net.ryian.flow.model.vo.WorkflowTemplateVO;

/**
 * @author allenwc
 * @date 2024/5/12 20:37
 */
public interface WorkflowTemplateService extends IService<WorkflowTemplatePO> {

    List<WorkflowTemplateVO> listOfficialTemplates();

    WorkflowTemplateVO getTemplate(String tid);

}
