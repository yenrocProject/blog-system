package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.java.fileService.FIleService;
import com.yenroc.ho.blogic.restDto.demo.DemoReqt;
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
import java.io.*;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private FIleService fIleService;

    private final String default_url = "/usr/local/webserver/nginx/html";
//    private final String default_url ="E:/heyanpeng/Desktop/3d相册源码";

    private final String default_url_user = default_url + "/{0}";
    private final String default_url_images = default_url + "/{0}/images";
    private final String default_url_m = default_url + "/{0}/m";
    private final String default_url_indexHtml = default_url + "/{0}/index.html";
    private final String default_url_mouldImg = default_url + "/{0}/images/{1}";

    @PostMapping("/albumUser/create")
    @ApiOperation(value="创建相册用户", notes="创建相册用户")
    public ResponseEntity<ResponseResult> albumUserCreate(@RequestBody AlbumUser albumUser) throws Exception {
        if (StringUtils.isBlank(albumUser.getName()) || StringUtils.isBlank(albumUser.getPassword())) {
            throw new BizLogicException(new SystemMessage("USER_NAME_NOT_BLANK","用户名或密码不能为空！"));
        }
        List<AlbumUser> selectResult = albumUserDao.select(albumUser);
        if (selectResult.size() > 0) {
            throw new BizLogicException(new SystemMessage("USER_NAME_EXIST","用户名已经存在"));
        }
//         //创建用户，需要生成文件夹，复制index 页面
//        String createFolderPath = MessageFormat.format(default_url_user, albumUser.getName());
//        File fileUIS = new File(createFolderPath);
//        if(!fileUIS.exists()){
//            log.info("创建用户文件夹路径=[{}]", createFolderPath);
//            fileUIS.setWritable(true, false);
//            fileUIS.mkdirs();// 创建用户文件夹

//            String imagesFolderPath = MessageFormat.format(default_url_images, albumUser.getName());
//            log.info("创建images文件夹路径=[{}]", imagesFolderPath);
//            fileUIS = new File(imagesFolderPath);
//            fileUIS.mkdirs();// images文件夹
//
//            String mFolderPath = MessageFormat.format(default_url_m, albumUser.getName());
//            log.info("创建m文件夹路径=[{}]", mFolderPath);
//            new File(mFolderPath).mkdir();// m文件夹

            // 复制demo 的index.html页面
            String fromIndexUrl = MessageFormat.format(default_url_indexHtml, "demo");
            String toIndexUrl = MessageFormat.format(default_url_indexHtml, albumUser.getName());
            this.fileCopy(fromIndexUrl, toIndexUrl);

            // 复制demo 的index.html页面
            fromIndexUrl = MessageFormat.format(default_url_indexHtml, "demo/m");
            toIndexUrl = MessageFormat.format(default_url_indexHtml, albumUser.getName() + "/m");
            this.fileCopy(fromIndexUrl, toIndexUrl);
//        }
        albumUser.setPassword(albumUser.getPassword());// TODO 加密
        albumUserDao.insert(albumUser);


        return new ResponseEntity<>(ResponseResult.success(null), HttpStatus.OK);
    }

    @PostMapping("/album/create")
    @ApiOperation(value="创建用户相册页面", notes="创建用户相册页面")
    public ResponseEntity<ResponseResult> albumCreate(@RequestBody Album album) throws Exception {
        List<Album> albumList = albumDao.select(album);
        if (albumList.size() > 0) {
            throw new BizLogicException(new SystemMessage("USER_NOT_CREATE","该用户已经创建该模板的相册页面，不能重复创建"));
        }
        AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(album.getAlbumTemplateId());
        if (albumTemplate == null) {
            throw new BizLogicException(new SystemMessage("ALBUM_TEMPLATE_NOT_FOUNT","相册模板不存在！"));
        }
        AlbumUser albumUser = albumUserDao.selectByPrimaryKey(album.getAlbumUserId());
        if (albumUser == null) {
            throw new BizLogicException(new SystemMessage("ALBUM_USER_NOT_FOUNT","当前用户不存在！"));
        }
        String imagesFolderPath = MessageFormat.format(default_url_mouldImg, albumUser.getName(), albumTemplate.getId());// 创建用户相册页面对应的images目录，

        log.info("创建imageMould文件夹路径=[{}]", imagesFolderPath);
        new File(imagesFolderPath).mkdir();


        // 创建相册页面的html（_1.html）
        String fromIndexUrl = MessageFormat.format(default_url_user, "demo")+"/_" + album.getAlbumTemplateId() +".html" ;
        String toIndexUrl = MessageFormat.format(default_url_user, albumUser.getName())+"/_" + album.getAlbumTemplateId() +".html" ;
        fileCopy(fromIndexUrl, toIndexUrl);
        // 创建相册页面的html（1.html）
        fromIndexUrl = MessageFormat.format(default_url_user, "demo")+"/" + album.getAlbumTemplateId() +".html" ;
        toIndexUrl = MessageFormat.format(default_url_user, albumUser.getName())+"/" + album.getAlbumTemplateId() +".html" ;
        fileCopy(fromIndexUrl, toIndexUrl);

        // m 的创建相册页面的html（_1.html）
        fromIndexUrl = MessageFormat.format(default_url_user, "demo")+"/m/_" + album.getAlbumTemplateId() +".html" ;
        toIndexUrl = MessageFormat.format(default_url_user, albumUser.getName())+"/m/_" + album.getAlbumTemplateId() +".html" ;
        fileCopy(fromIndexUrl, toIndexUrl);
        // 创建相册页面的html（mould.html）
        fromIndexUrl = MessageFormat.format(default_url_user, "demo")+"/m/" + album.getAlbumTemplateId() +".html" ;
        toIndexUrl = MessageFormat.format(default_url_user, albumUser.getName())+"/m/" + album.getAlbumTemplateId() +".html" ;
        fileCopy(fromIndexUrl, toIndexUrl);


        // 生成模板页面对应的照片实例
        AlbumTemplatePhotoConfig albumTemplatePhotoConfig = new AlbumTemplatePhotoConfig();
        albumTemplatePhotoConfig.setAlbumTemplateId(album.getAlbumTemplateId());
        List<AlbumTemplatePhotoConfig> photoConfigList = albumTemplatePhotoConfigDao.select(albumTemplatePhotoConfig);

        album.setId(UUID.randomUUID().toString());
        Integer albumId = albumDao.insert(album);

        if (photoConfigList != null && photoConfigList.size() > 0) {
            for (AlbumTemplatePhotoConfig apc : photoConfigList) {
                AlbumPhoto albumPhoto = new AlbumPhoto();
                albumPhoto.setAlbumId(album.getId());
                albumPhoto.setTemplatePhotoId(apc.getId());
                albumPhotoDao.insert(albumPhoto);
            }
        }

        return new ResponseEntity<>(ResponseResult.success(null), HttpStatus.OK);
    }


    @PostMapping("/albumPhoto/upload/{id}")
    @ApiOperation(value="用户上传图片", notes="用户上传图片")
    public ResponseEntity<ResponseResult> albumPhotoUpload(@PathVariable String id,HttpServletRequest request) throws Exception {
        AlbumPhoto albumPhoto = albumPhotoDao.selectByPrimaryKey(id);
        if (albumPhoto == null) {
            throw new BizLogicException(new SystemMessage("NOT_UPLOAD","不允许上传！"));
        }
        Album album = albumDao.selectByPrimaryKey(albumPhoto.getAlbumId());
        AlbumUser albumUser = albumUserDao.selectByPrimaryKey(album.getAlbumUserId());
        AlbumTemplatePhotoConfig photoConfig = albumTemplatePhotoConfigDao.selectByPrimaryKey(albumPhoto.getTemplatePhotoId());

        // 获取文件上传路径
        String fileFullPath = MessageFormat.format(default_url_images, albumUser.getName()) + "/" + album.getAlbumTemplateId() + "/" +  photoConfig.getAlpumPhotoName() + ".jpg";

        MultipartHttpServletRequest multipartHttpServletRequest = null;
        try {
            multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        } catch (Exception e) {
            log.error("获取文件信息出现错误", e);
            throw new BizLogicException(new SystemMessage("GET_FILE_ERROR","获取文件信息出现错误"));
        }
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        File fullPathFile = new File(fileFullPath);
        try {
            file.transferTo(fullPathFile); //保存文件
        } catch (IOException | IllegalStateException ex) {
            log.error("文件=[{}] 上传失败，异常信息=[{}]", file.getOriginalFilename(), ex);
            throw new BizLogicException(new SystemMessage("FILE_UPLOAD_ERROR","文件上传失败!"));
        }
        albumPhoto.setPhotoUrl(fileFullPath);
        albumPhotoDao.updateByPrimaryKey(albumPhoto);

        return new ResponseEntity<>(ResponseResult.success(null), HttpStatus.OK);
    }

    private void fileCopy(String fromUrl, String toUrl) throws Exception {
        log.info("copy file fromUrl=[{}],toUrl=[{}]", fromUrl, toUrl);

        // 判断文件目录是否存在，不存在则创建
        String foloder = StringUtils.substringBeforeLast(toUrl,"/");
        File fileUIS = new File(foloder);
        if(!fileUIS.exists()) {//
            log.info("创建文件夹路径=[{}]", foloder);
            fileUIS.setWritable(true, false);
            fileUIS.mkdirs();// 创建用户文件夹
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fromUrl);//创建输入流对象
            fos = new FileOutputStream(toUrl); //创建输出流对象
            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!= -1){ //循环读取数据
                fos.write(datas,0,len);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fis.close();//释放资源
            fos.close();//释放资源
        }
    }


}
