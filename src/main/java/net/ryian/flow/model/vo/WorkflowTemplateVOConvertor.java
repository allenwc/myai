package net.ryian.flow.model.vo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.WorkflowTemplatePO;

/**
 * @author allenwc
 * @date 2024/5/12 20:55
 */
@Mapper
public interface WorkflowTemplateVOConvertor {

    WorkflowTemplateVOConvertor INSTANCE = Mappers.getMapper(WorkflowTemplateVOConvertor.class);

    @Mapping(target = "id",source = "tid")
    @Mapping(target = "images",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToList(po.getImages()))")
    @Mapping(target = "data",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToJson(po.getData()))")
    WorkflowTemplateVO toVO(WorkflowTemplatePO po);

    List<WorkflowTemplateVO> toVOList(List<WorkflowTemplatePO> pos);

}
