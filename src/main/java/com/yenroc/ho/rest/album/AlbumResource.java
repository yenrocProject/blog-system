package com.yenroc.ho.rest.album;

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


    @PostMapping("/albumUser/createAndLogin")
    @ApiOperation(value="创建用户或登录", notes="创建用户或登录")
    public ResponseEntity<ResponseResult> UserCreate(@RequestBody UserCreateAndLoginReqt param) throws Exception {
        log.info("REST 创建用户或登录开始,入参=[{}]", param);
        UserCreateAndLoginResp execute = userCreateAndLoginBLogic.execute(param);
        return new ResponseEntity<>(ResponseResult.success(execute), HttpStatus.OK);
    }

}
