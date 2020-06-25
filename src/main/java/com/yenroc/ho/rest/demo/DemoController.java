package com.yenroc.ho.rest.demo;

import com.yenroc.ho.blogic.java.demo.DemoService;
import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.blogic.restDto.demo.DemoResp;
import com.yenroc.ho.blogic.restDto.demo.DemoRespS01;

import com.yenroc.ho.common.bean.PageInfo;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.consts.CommonConsts;
import com.yenroc.ho.common.context.BLogContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Api(value = "DemoController")
@RestController
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    /**
     * 测试restFul的接口
     */
    @Autowired
    private DemoService demoService;
    
    /**
     * Get  /demo/get/{id} : getById
     */
    @GetMapping("/demo/get/{id}")
    @ApiOperation(value="mybatisplus的getById", notes="mybatisplus的getById")
    public ResponseEntity<ResponseResult> getById(@PathVariable String id) throws Exception {
        log.debug("mybatisplus getById: {}", id);
        DemoReqt demoReqt = new DemoReqt();
        DemoRespS01 result = demoService.getById(id);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    /**
     * Get  /demo/insert : 测试功能
     */
    @PostMapping("/demo/insert2")
    @ApiOperation(value="mybatisplus的insert功能", notes="mybatisplus的insert功能")
    public ResponseEntity<ResponseResult> demoInsert(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("mybatisplus demoInsert: {}", demoReqt);
        String id = demoService.insert(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(id), HttpStatus.OK);
    }

    /**
     * Get  /demo/insert : 测试功能
     */
    @GetMapping("/demo/selectAll")
    @ApiOperation(value="mybatisplus的selectAll功能", notes="mybatisplus的selectAll功能")
    public ResponseEntity<ResponseResult> selectAll() throws Exception {
        log.debug("mybatisplus selectAll: {}");
        DemoResp result = demoService.selectAll();
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }
    
    /**
     * POST  /demo/query : 测试功能
     */
    @PostMapping("/demo/query")
    @ApiOperation(value="测试查询功能", notes="测试查询功能")
    public ResponseEntity<ResponseResult> query(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("mybatisplus query: {}", demoReqt);
        DemoResp result = demoService.queryDemo(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    /**
     * POST  /demo/query/page : 测试功能
     */
    @PostMapping("/demo/query/page")
    @ApiOperation(value="测试分页查询功能", notes="测试分页查询功能")
    public ResponseEntity<ResponseResult> queryBypage(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("mybatisplus queryBypage: {}", demoReqt);

        // 分页测试
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(3);
        pageInfo.setOrderBy("age desc");
        BLogContext.getMap().put(CommonConsts.PAGE_INFO, pageInfo);

        DemoResp result = demoService.queryDemo(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

}
