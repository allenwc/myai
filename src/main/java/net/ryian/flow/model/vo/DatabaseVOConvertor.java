package net.ryian.flow.model.vo;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.UserVectorDatabasePO;

/**
 * @author allenwc
 * @date 2024/5/4 00:10
 */
@Mapper
public interface DatabaseVOConvertor {

    DatabaseVOConvertor INSTANCE = Mappers.getMapper(DatabaseVOConvertor.class);

    @Mapping(target = "id", source = "vid")
    UserVectorDatabasePO toPO(DatabaseVO vo);


    List<DatabaseVO> toVOList(List<UserVectorDatabasePO> pos);

    @Mapping(target = "vid", source = "id")
    DatabaseVO toVO(UserVectorDatabasePO po);
}
