package net.ryian.flow.model.vo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.WorkflowPO;

/**
 * @author allenwc
 * @date 2024/5/4 09:26
 */
@Mapper
public interface WorkflowVOConvertor {

    WorkflowVOConvertor INSTANCE = Mappers.getMapper(WorkflowVOConvertor.class);

    @Mapping(target = "wid", source = "wid")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "brief", source = "brief")
    @Mapping(target = "images",expression = "java(net.ryian.flow.common.utils.JsonUtil.listToString(vo.getImages()))")
    @Mapping(target = "tags",expression = "java(net.ryian.flow.common.utils.JsonUtil.listToString(vo.getTags()))")
    @Mapping(target = "data",expression = "java(net.ryian.flow.common.utils.JsonUtil.jsonToString(vo.getData()))")
    WorkflowPO toPO(WorkflowVO vo);

    @Mapping(target = "wid", source = "wid")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "brief", source = "brief")
    @Mapping(target = "images",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToList(po.getImages()))")
    @Mapping(target = "tags",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToList(po.getImages()))")
    @Mapping(target = "data",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToJson(po.getData()))")
    WorkflowVO toVO(WorkflowPO po);

    List<WorkflowVO> toVOList(List<WorkflowPO> pos);

}
