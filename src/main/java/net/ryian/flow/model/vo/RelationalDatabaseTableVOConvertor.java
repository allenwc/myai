package net.ryian.flow.model.vo;

import org.mapstruct.factory.Mappers;

import net.ryian.flow.model.po.UserRelationalTablePO;

/**
 * @author allenwc
 * @date 2024/5/9 10:11
 */
public interface RelationalDatabaseTableVOConvertor {

    RelationalDatabaseTableVOConvertor INSTANCE = Mappers.getMapper(RelationalDatabaseTableVOConvertor.class);

    RelationalDatabaseTableListVO toVO(UserRelationalTablePO po);

}
