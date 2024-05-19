package net.ryian.flow.model.vo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.UserSettingPO;

/**
 * @author allenwc
 * @date 2024/5/16 09:08
 */
@Mapper
public interface UserSettingVOConvertor {

    UserSettingVOConvertor INSTANCE = Mappers.getMapper(UserSettingVOConvertor.class);

    @Mapping(target = "data",expression = "java(net.ryian.flow.common.utils.JsonUtil.stringToJson(po.getData()))")
    UserSettingVO toVO(UserSettingPO po);

}
