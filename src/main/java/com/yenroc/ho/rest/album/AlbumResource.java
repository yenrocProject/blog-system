package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumReqt;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumResp;
import com.yenroc.ho.blogic.restDto.album.photoUpdate.PhotoUpdateReqt;
import com.yenroc.ho.blogic.restDto.album.photoUpdate.PhotoUpdateResp;
import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginReqt;
import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginResp;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.service.BizLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Api(value = "AlbumResource")
@RestController
public class AlbumResource {

    private static final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    @Autowired
    @Qualifier("UserCreateAndLoginBLogic")
    private BizLogic<UserCreateAndLoginReqt, UserCreateAndLoginResp> userCreateAndLoginBLogic;

    @Autowired
    @Qualifier("CreateOrUpdateAlbumBLogic")
    private BizLogic<CreateOrUpdateAlbumReqt, CreateOrUpdateAlbumResp> createOrUpdateAlbumBLogic;

    @Autowired
    @Qualifier("PhotoUpdateBLogic")
    private BizLogic<PhotoUpdateReqt, PhotoUpdateResp> photoUpdateBLogic;


    @PostMapping("/albumUser/createAndLogin")
    @ApiOperation(value="创建用户或登录", notes="创建用户或登录")
    public ResponseEntity<ResponseResult> UserCreate(@RequestBody UserCreateAndLoginReqt param) throws Exception {
        log.info("REST 创建用户或登录开始,入参=[{}]", param);
        UserCreateAndLoginResp execute = userCreateAndLoginBLogic.execute(param);
        return new ResponseEntity<>(ResponseResult.success(execute), HttpStatus.OK);
    }

    @PostMapping("/album/createOrUpdate")
    @ApiOperation(value="创建用户或登录", notes="创建用户或登录")
    public ResponseEntity<ResponseResult> createOrUpate(@RequestBody CreateOrUpdateAlbumReqt param) throws Exception {
        log.info("REST 创建或者更新相册信息接口开发=[{}]", param);
        CreateOrUpdateAlbumResp execute = createOrUpdateAlbumBLogic.execute(param);
        return new ResponseEntity<>(ResponseResult.success(execute), HttpStatus.OK);
    }

    @PostMapping("/album/photoUpdate")
    @ApiOperation(value="相册照片更新", notes="相册照片更新")
    public ResponseEntity<ResponseResult> photoUpdate(@RequestBody PhotoUpdateReqt param) throws Exception {
        log.info("REST 相册照片更新=[{}]", param);
        PhotoUpdateResp execute = photoUpdateBLogic.execute(param);
        return new ResponseEntity<>(ResponseResult.success(execute), HttpStatus.OK);
    }
}
