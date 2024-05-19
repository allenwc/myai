package net.ryian.flow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.model.vo.DatabaseVO;
import net.ryian.flow.model.vo.DatabaseVOConvertor;
import net.ryian.flow.model.vo.RelationalDatabaseVO;
import net.ryian.flow.model.vo.RelationalDatabaseVOConvertor;
import net.ryian.flow.service.DatabaseService;
import net.ryian.flow.service.RelationalDatabaseService;

/**
 * @author allenwc
 * @date 2024/5/3 12:00
 */
@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    private final RelationalDatabaseService relationalDatabaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService,RelationalDatabaseService relationalDatabaseService) {
        this.databaseService = databaseService;
        this.relationalDatabaseService = relationalDatabaseService;
    }

    @PostMapping("/api/database__list")
    public ServerResponseEntity<List<DatabaseVO>> list() {
        return ServerResponseEntity.success(DatabaseVOConvertor.INSTANCE.toVOList(databaseService.list()));
    }

    @PostMapping("/api/database__create")
    public ServerResponseEntity<Void> create(@RequestBody DatabaseVO databaseVO) {
        databaseService.save(DatabaseVOConvertor.INSTANCE.toPO(databaseVO));
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/database__delete")
    public ServerResponseEntity<Void> delete(@RequestBody DatabaseVO databaseVO) {
        databaseService.removeById(databaseVO.getVid());
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/relational_database__list")
    public ServerResponseEntity<List<RelationalDatabaseVO>> listRelationalDatabases() {
        return ServerResponseEntity.success(RelationalDatabaseVOConvertor.INSTANCE.toVOList(relationalDatabaseService.list()));
    }

    @PostMapping("/api/relational_database__create")
    public ServerResponseEntity<Void> createRelationalDatabase(@RequestBody RelationalDatabaseVO relationalDatabaseVO) {
        relationalDatabaseService.save(RelationalDatabaseVOConvertor.INSTANCE.toPO(relationalDatabaseVO));
        return ServerResponseEntity.success();
    }

    @PostMapping("/api/relational_database__delete")
    public ServerResponseEntity<Void> deleteRelationalDatabase(@RequestBody RelationalDatabaseVO relationalDatabaseVO) {
        relationalDatabaseService.removeById(relationalDatabaseVO.getRid());
        return ServerResponseEntity.success();
    }

}
