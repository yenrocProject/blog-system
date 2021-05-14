package com.yenroc.ho.blogic.java.album.impl;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.album.ViewAlbumService;
import com.yenroc.ho.blogic.restDto.album.viewAlbum.AlbumPhotoInfoVo;
import com.yenroc.ho.blogic.sqlDto.albumPhotoInstance.AlbumPhotoInfo;
import com.yenroc.ho.config.BlogGlobalConfig;
import com.yenroc.ho.mapper.AlbumInstanceDao;
import com.yenroc.ho.mapper.AlbumPhotoInstanceDao;
import com.yenroc.ho.mapper.AlbumTemplateDao;
import com.yenroc.ho.mapper.UserDao;
import com.yenroc.ho.mapper.entity.AlbumInstance;
import com.yenroc.ho.mapper.entity.AlbumPhotoInstance;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import com.yenroc.ho.mapper.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ViewAlbumServiceImpl implements ViewAlbumService {

    private static final Logger log = LoggerFactory.getLogger(ViewAlbumServiceImpl.class);

    @Autowired
    private AlbumTemplateDao albumTemplateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AlbumInstanceDao albumInstanceDao;

    @Autowired
    private AlbumPhotoInstanceDao albumPhotoInstanceDao;

    @Autowired
    private BlogGlobalConfig blogGlobalConfig;

    /**
     * 预览模板相册
     * @param templateId
     * @return
     */
    public ModelAndView previewAlbum(Integer templateId){
        AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(templateId);
        if (albumTemplate == null) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg","预览出现错误了~~");
            mv.setViewName("404.html");
            return mv;
        }
        ModelAndView mv = viewAlbumId(albumTemplate.getId());
        mv.addObject("userName", "defalut");
        return mv;
    }

    /**
     * 查看用户相册
     * @param userName
     * @param templateId
     * @param albumId
     * @return
     */
    public ModelAndView viewUserAlbum(String userName, Integer templateId, Integer albumId, String privateKey) {
        List<User> users = userDao.finByUserName(userName);
        if (users.size() == 0) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg","查看出错啦~~");
            mv.setViewName("404.html");
            return mv;
        }
        AlbumInstance albumInstance = albumInstanceDao.selectByPrimaryKey(albumId);
        if (albumInstance == null
                || !albumInstance.getUserId().equals(users.get(0).getId())
                || !albumInstance.getAlbumTemplateId().equals(templateId)) {// 相册没有数据或者和userId不一致,或者和模板不对
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg","没有找到相册信息,请检查url路径~~");
            mv.setViewName("404.html");
            return mv;
        }
        if (AlbumConsts.ALBUM_VIEW_PRIVATE.equals(albumInstance.getPrivateView())) {// 如果设置为私有,则验证密钥是否正确
            if (!StringUtils.equals(albumInstance.getPrivateKey(), privateKey)) {
                log.warn("相册=[{}]设置不公开,密钥=[{}],输入的密钥为=[{}], 密钥不匹配", albumInstance.getId(),
                        albumInstance.getPrivateKey(),
                        privateKey);
                ModelAndView mv = new ModelAndView();
                mv.addObject("errorMsg","密钥不匹配,无权访问该页面~~");
                mv.setViewName("error.html");
                return mv;
            }
        }

        return viewAlbumId(albumId);
    }

    public ModelAndView viewAlbumId(Integer albumId) {
        AlbumInstance albumInstance = albumInstanceDao.selectByPrimaryKey(albumId);
        if (albumInstance == null) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("errorMsg","没有找到相册信息,请检查url路径~~");
            mv.setViewName("404.html");
            return mv;
        }
        return viewAlbum(albumInstance);
    }

    public ModelAndView viewAlbum(AlbumInstance albumInstance) {
        ModelAndView mv = new ModelAndView();

        // 根据相册获取模板的信息
        AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(albumInstance.getAlbumTemplateId());

        mv.setViewName(albumTemplate.getTemplateHtmlPath());

        List<AlbumPhotoInfo> albumPhotoInfos = albumPhotoInstanceDao.getPhotoInfoByAlbumId(albumInstance.getId());

        // TODO 测试
        mv.addObject("images",new String[]{"/temp1/images/1/1.jpg","/temp1/images/1/1.jpg","/temp1/images/1/1.jpg",
                "/temp1/images/1/1.jpg","/temp1/images/1/1.jpg","/temp1/images/1/1.jpg","/temp1/images/1/1.jpg",
                "/temp1/images/1/1.jpg","/temp1/images/1/1.jpg","/temp1/images/1/1.jpg","/temp1/images/1/1.jpg",
                "/temp1/images/1/1.jpg"});

        List<AlbumPhotoInfoVo> albumPhotoInfoVos = new ArrayList<>();
        if (albumPhotoInfos.size() > 0) {
            for (AlbumPhotoInfo albumPhotoInfo : albumPhotoInfos) {
                AlbumPhotoInfoVo albumPhotoInfoVo = new AlbumPhotoInfoVo();
                albumPhotoInfoVo.setAlbumPhotoInstanceId(albumPhotoInfo.getAlbumPhotoInstanceId());
                // 自带提供的预览
                albumPhotoInfoVo.setFileName("/api/file/preview/" + albumPhotoInfo.getFileName());
                // 通过文件服务器提供的预览,可使用nginx
                albumPhotoInfoVo.setPhotoUrl(blogGlobalConfig.getPhotoViewUrl() + albumPhotoInfo.getPhotoUrl());
            }
        }
        log.info("查看相册,图片信息=[{}]", albumPhotoInfoVos);
        mv.addObject("imageInfos",albumPhotoInfoVos);

        return mv;
    }

}
