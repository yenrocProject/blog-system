package com.yenroc.ho.rest.home;

import com.yenroc.ho.blogic.java.fileService.FIleService;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.mapper.*;
import com.yenroc.ho.mapper.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api")
@Api(value = "MyAlbumResource")
@RestController
public class MyAlbumResource {

    private static final Logger log = LoggerFactory.getLogger(MyAlbumResource.class);

    @Autowired
    private AlbumUserDao albumUserDao;


    @PostMapping("/albumUser/createAndLogin")
    @ApiOperation(value="创建相册用户", notes="创建相册用户")
    public ResponseEntity<ResponseResult> albumUserCreate(@RequestBody AlbumUser albumUser) throws Exception {
        if (StringUtils.isBlank(albumUser.getName()) || StringUtils.isBlank(albumUser.getPassword())) {
            throw new BizLogicException(new SystemMessage("USER_NAME_NOT_BLANK","用户名或密码不能为空！"));
        }
        AlbumUser selectUser = new AlbumUser();
        selectUser.setName(albumUser.getName());
        List<AlbumUser> selectResult = albumUserDao.select(selectUser);
        if (selectResult.size() > 0) {
            AlbumUser albumUser1 = selectResult.get(0);
            if (!albumUser1.getPassword().equals(albumUser.getPassword())) {
                throw new BizLogicException(new SystemMessage("USER_PASSWOED_ERROR","用户名口令错误！"));
            } else {
                albumUser = albumUser1;
                log.info("用户=[{}]登录成功!", albumUser.getName());
            }
        } else {
            albumUser.setPassword(albumUser.getPassword());
            albumUserDao.insert(albumUser);
            log.info("用户=[{}]创建成功", albumUser.getName());
        }
        return new ResponseEntity<>(ResponseResult.success(albumUser), HttpStatus.OK);
    }

}
