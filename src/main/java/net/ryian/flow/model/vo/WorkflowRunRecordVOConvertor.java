package net.ryian.flow.model.vo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.WorkflowRunRecordPO;

/**
 * @author allenwc
 * @date 2024/5/4 19:26
 */
@Mapper
public interface WorkflowRunRecordVOConvertor {

    WorkflowRunRecordVOConvertor INSTANCE = Mappers.getMapper(WorkflowRunRecordVOConvertor.class);

    @Mapping(target = "data",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToJson(po.getData()))")
    WorkflowRunRecordVO toVO(WorkflowRunRecordPO po);

    List<WorkflowRunRecordVO> toVOList(List<WorkflowRunRecordPO> pos);

}
