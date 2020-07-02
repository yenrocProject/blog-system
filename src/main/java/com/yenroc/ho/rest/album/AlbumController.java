package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.blogic.restDto.demo.DemoRespS01;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.mapper.*;
import com.yenroc.ho.mapper.entity.*;
import com.yenroc.ho.rest.demo.DemoController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@Api(value = "AlbumController")
@RestController
public class AlbumController {

    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumUserDao albumUserDao;
    @Autowired
    private AlbumPhotoDao albumPhotoDao;
    @Autowired
    private AlbumTemplateDao albumTemplateDao;
    @Autowired
    private AlbumTemplatePhotoConfigDao albumTemplatePhotoConfigDao;


    @PostMapping("/album/query")
    @ApiOperation(value="相册查询", notes="相册查询")
    public ResponseEntity<ResponseResult> albumQuery(@RequestBody Album params) throws Exception {
        List<Album> result = albumDao.select(params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/albumUser/query")
    @ApiOperation(value="用户查询", notes="用户查询")
    public ResponseEntity<ResponseResult> albumUserQuery(@RequestBody AlbumUser params) throws Exception {
        List<AlbumUser> result = albumUserDao.select(params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/albumPhoto/query")
    @ApiOperation(value="相册照片查询", notes="相册照片查询")
    public ResponseEntity<ResponseResult> albumUserQuery(@RequestBody AlbumPhoto params) throws Exception {
        List<AlbumPhoto> result = albumPhotoDao.select(params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/albumTemplate/query")
    @ApiOperation(value="相册模板查询", notes="相册模板查询")
    public ResponseEntity<ResponseResult> albumUserQuery(@RequestBody AlbumTemplate params) throws Exception {
        List<AlbumTemplate> result = albumTemplateDao.select(params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

    @PostMapping("/albumTemplatePhotoConfig/query")
    @ApiOperation(value="相册模板查询", notes="相册模板查询")
    public ResponseEntity<ResponseResult> albumTemplatePhotoConfigQuery(@RequestBody AlbumTemplatePhotoConfig params) throws Exception {
        List<AlbumTemplatePhotoConfig> result = albumTemplatePhotoConfigDao.select(params);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }



}
