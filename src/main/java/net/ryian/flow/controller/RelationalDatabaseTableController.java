package net.ryian.flow.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONArray;

import net.ryian.flow.common.response.ServerResponseEntity;
import net.ryian.flow.model.vo.RelationalDatabaseTableListVO;
import net.ryian.flow.model.vo.param.RelationalDatabaseTableListParam;
import net.ryian.flow.service.UserRelationalTableService;

/**
 * @author allenwc
 * @date 2024/5/8 21:31
 */
@RestController
public class RelationalDatabaseTableController {

    private UserRelationalTableService userRelationalTableService;

    @Autowired
    public RelationalDatabaseTableController(UserRelationalTableService userRelationalTableService) {
        this.userRelationalTableService = userRelationalTableService;
    }

    @PostMapping("/api/relational_database_table__list")
    public ServerResponseEntity<RelationalDatabaseTableListVO> list(@RequestBody RelationalDatabaseTableListParam param) {
        RelationalDatabaseTableListVO vo = new RelationalDatabaseTableListVO();
        vo.setTables(userRelationalTableService.listByParam(param));
        return ServerResponseEntity.success(vo);
    }

}
