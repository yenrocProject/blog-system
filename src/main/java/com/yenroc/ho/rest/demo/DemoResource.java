package com.yenroc.ho.rest.demo;

import com.yenroc.ho.blogic.java.demo.DemoBLogic;
import com.yenroc.ho.blogic.restDto.demo.DemoRespS01;
import com.yenroc.ho.common.bean.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.blogic.restDto.demo.DemoResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@RequestMapping("/api")
@Api(value = "DemoResource")
@RestController
public class DemoResource {

    private static final Logger log = LoggerFactory.getLogger(DemoResource.class);
    
    /**
     * 测试restFul的接口
     */
    @Autowired
    private DemoBLogic demoBLogic = null;
    
    /**
     * Get  /mapper/{id} : 测试功能
     */
    @GetMapping("/demo/{id}")
    @ApiOperation(value="测试功能", notes="测试功能")
    public ResponseEntity<ResponseResult> demoById(@PathVariable String id) throws Exception {
        log.debug("测试restful demoById : {}", id);
        DemoReqt demoReqt = new DemoReqt();
        demoReqt.setId(id);
        DemoResp result = demoBLogic.executeQuery(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }
    
    @PostMapping("/demo/page")
    @ApiOperation(value="测试功能", notes="测试功能")
    public ResponseEntity<ResponseResult> demoPage(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("测试restful demoPage : {}", demoReqt);
        DemoResp result = demoBLogic.executeQuery(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/demo/insert")
    @ApiOperation(value="测试功能", notes="测试功能")
    public ResponseEntity<ResponseResult> demoInsert(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("测试restful demoInsert : {}", demoReqt);
        String result = demoBLogic.executeInsert(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PutMapping("/demo/update")
    @ApiOperation(value="测试功能", notes="测试功能")
    public ResponseEntity<ResponseResult> demoUpdate(@RequestBody DemoReqt demoReqt) throws Exception {
        log.debug("测试restful demoUpdate : {}", demoReqt);
        DemoRespS01 result = demoBLogic.executeUpdate(demoReqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }
    
}
