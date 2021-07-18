package com.yenroc.ho.rest.common;

import com.yenroc.ho.blogic.java.common.CommonService;
import com.yenroc.ho.common.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一开放api ,提供对表的增删改查..
 *
 */
@RequestMapping("/api")
@Api(value = "CommonController")
@RestController
public class CommonController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    @GetMapping("/query/{tableName}/{id}")
    @ApiOperation(value="统一开放api的通过Id查询接口", notes="统一开放api的通过Id查询接口")
    public ResponseEntity<ResponseResult> query(@PathVariable String tableName, @PathVariable String id) throws Exception {
        log.debug("统一开放api的查询表=[{}],Id=[{}]的数据请求", tableName, id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Object result = commonService.select(tableName, params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/query/{tableName}/list")
    @ApiOperation(value="统一开放api 进行表数据的查询", notes="统一开放api 进行表数据的查询")
    public ResponseEntity<ResponseResult> query(@PathVariable String tableName,
                                                    @RequestBody Map<String, Object> params) throws Exception {
        log.debug("统一开放api 进行表=[{}]数据的查询,查询条件=[{}]", tableName, params);
        Object result = commonService.select(tableName, params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/insert/{tableName}")
    @ApiOperation(value="统一开放api 进行表数据的创建", notes="统一开放api 进行表数据的创建")
    public ResponseEntity<ResponseResult> insert(@PathVariable String tableName,
                                                    @RequestBody Map<String, Object> params) throws Exception {
        log.debug("统一开放api 进行表=[{}]数据的创建=[{}]", tableName, params);
        Object result = commonService.insert(tableName, params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PutMapping("/update/{tableName}/{id}")
    @ApiOperation(value="统一开放api 根据id进行表数据的更新", notes="统一开放api 根据id进行表数据的更新")
    public ResponseEntity<ResponseResult> update(@PathVariable String tableName,
                                                 @PathVariable String id,
                                                 @RequestBody Map<String, Object> params) throws Exception {
        log.debug("统一开放api 根据id=[{}]进行表=[{}]数据的更新=[{}]", id, tableName, params);
        params.put("id", id);
        int result = commonService.update(tableName, params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{tableName}/{id}")
    @ApiOperation(value="统一开放api 根据id进行表数据的删除", notes="统一开放api 根据id进行表数据的删除")
    public ResponseEntity<ResponseResult> delete(@PathVariable String tableName, @PathVariable String id) throws Exception {
        log.debug("统一开放api 根据id=[{}]进行表=[{}]数据的删除", id, tableName);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int result = commonService.delete(tableName, params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }


}
