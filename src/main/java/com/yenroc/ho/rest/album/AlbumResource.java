package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.mapper.*;
import com.yenroc.ho.mapper.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api")
@Api(value = "AlbumResource")
@RestController
public class AlbumResource {

    private static final Logger log = LoggerFactory.getLogger(AlbumResource.class);

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

    private final String default_url = "/usr/local/webserver/nginx/html";

    @PostMapping("/albumUser/insert")
    @ApiOperation(value="创建根相册", notes="创建根相册")
    public ResponseEntity<ResponseResult> demoInsert(@RequestBody AlbumUser albumUser) throws Exception {


        return new ResponseEntity<>(ResponseResult.success(null), HttpStatus.OK);
    }

    @PostMapping("/albumPhoto/upload")
    @ApiOperation(value="上传相册", notes="上传相册")
    public ResponseEntity<ResponseResult> demoInsert(@RequestBody AlbumPhoto albumPhoto, HttpServletRequest request) throws Exception {


        return new ResponseEntity<>(ResponseResult.success(null), HttpStatus.OK);
    }


}
