package net.ryian.flow.model.vo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.UserRelationalDatabasePO;

/**
 * @author allenwc
 * @date 2024/5/4 00:10
 */
@Mapper
public interface RelationalDatabaseVOConvertor {

    RelationalDatabaseVOConvertor INSTANCE = Mappers.getMapper(RelationalDatabaseVOConvertor.class);

    @Mapping(target = "id", source = "rid")
    UserRelationalDatabasePO toPO(RelationalDatabaseVO vo);


    List<RelationalDatabaseVO> toVOList(List<UserRelationalDatabasePO> pos);

    @Mapping(target = "rid", source = "id")
    RelationalDatabaseVO toVO(UserRelationalDatabasePO po);
}
