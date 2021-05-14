package com.yenroc.ho.blogic.java.album.impl;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.album.UserAlbumInfoService;
import com.yenroc.ho.blogic.restDto.album.userAlbumInfo.AlbumTemplateVo;
import com.yenroc.ho.blogic.restDto.album.userAlbumInfo.PublicAlbumVo;
import com.yenroc.ho.blogic.restDto.album.userAlbumInfo.UserAlbumVo;
import com.yenroc.ho.config.BlogGlobalConfig;
import com.yenroc.ho.mapper.AlbumInstanceDao;
import com.yenroc.ho.mapper.AlbumTemplateDao;
import com.yenroc.ho.mapper.UserDao;
import com.yenroc.ho.mapper.entity.AlbumInstance;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import com.yenroc.ho.mapper.entity.User;
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
public class UserAlbumInfoServiceImpl implements UserAlbumInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserAlbumInfoServiceImpl.class);


    @Autowired
    private UserDao userDao;

    @Autowired
    private AlbumTemplateDao albumTemplateDao;

    @Autowired
    private AlbumInstanceDao albumInstanceDao;

    @Autowired
    private BlogGlobalConfig blogGlobalConfig;

    public ModelAndView showAlbums(String userName) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userName", userName);

        List<User> users = userDao.finByUserName(userName);
        if (users.size() == 0) {
            mv.addObject("errorMsg","页面找不到了~~");
            mv.setViewName("404.html");
            return mv;
        }
        User user = users.get(0);

        // 所有的模板
        List<AlbumTemplate> albumTemplates = albumTemplateDao.selectAll();
        List<AlbumTemplateVo> albumTemplateVos = new ArrayList<>();
        if (albumTemplates.size() > 0) {
            for (AlbumTemplate albumTemplate : albumTemplates) {
                AlbumTemplateVo albumTemplateVo = new AlbumTemplateVo();
                albumTemplateVo.setTemplateId(albumTemplate.getId());
                albumTemplateVo.setDefaultInstanceId(albumTemplate.getDefaultInstanceId());
                albumTemplateVo.setTemplateName(albumTemplate.getTemplateName());
                albumTemplateVo.setTemplateDesc(albumTemplate.getTemplateDesc());
                albumTemplateVo.setTemplateStyleCss(albumTemplate.getTemplateStyleCss());
                albumTemplateVos.add(albumTemplateVo);
            }
        }

        // 用户的模板
        List<AlbumInstance> userAlbumInstances = albumInstanceDao.finByUserId(user.getId());
        List<UserAlbumVo> userAlbumVos = new ArrayList<>();
        if (userAlbumInstances.size() > 0) {
            for (AlbumInstance albumInstance : userAlbumInstances) {
                UserAlbumVo userAlbumVo = new UserAlbumVo();
                userAlbumVo.setTemplateId(albumInstance.getAlbumTemplateId());
                userAlbumVo.setUserAlbumId(albumInstance.getId());
                userAlbumVo.setAlbumName(albumInstance.getAlbumName());
                userAlbumVo.setAlbumDesc(albumInstance.getAlbumDesc());
                userAlbumVo.setPrivateKey(albumInstance.getPrivateKey());
                userAlbumVo.setAlbumStyleCss(albumInstance.getAlbumStyleCss());
                userAlbumVo.setDefaultViewPhoto(blogGlobalConfig.getPhotoViewUrl() + albumInstance.getDefaultViewPhoto());
                userAlbumVos.add(userAlbumVo);
            }
        }

        // 所用用户的实例
        List<AlbumInstance> albumInstances = albumInstanceDao.selectAll();
        List<PublicAlbumVo> publicAlbumVos = new ArrayList<>();
        if (albumInstances.size() > 0) {
            for (AlbumInstance albumInstance : albumInstances) {
                if (AlbumConsts.ALBUM_VIEW_PRIVATE.equals(albumInstance.getPrivateView())) {
                    continue;
                }
                boolean isDefault =false;
                for (AlbumTemplateVo albumTemplateVo : albumTemplateVos) {
                    if (albumTemplateVo.getDefaultInstanceId().equals(albumInstance.getId())) {
//                        albumTemplateVo.setViewPhotoUrl(albumInstance.getDefaultViewPhoto());
                        albumTemplateVo.setViewPhotoUrl(blogGlobalConfig.getPhotoViewUrl() + albumInstance.getDefaultViewPhoto());

                        isDefault = true;
                        break;
                    }
                }
                if (isDefault) {
                    continue;
                }

                PublicAlbumVo publicAlbumVo = new PublicAlbumVo();
                publicAlbumVo.setTemplateId(albumInstance.getAlbumTemplateId());
                publicAlbumVo.setAlbumId(albumInstance.getId());
                publicAlbumVo.setAlbumName(albumInstance.getAlbumName());
                publicAlbumVo.setAlbumDesc(albumInstance.getAlbumDesc());
                publicAlbumVo.setAlbumStyleCss(albumInstance.getAlbumStyleCss());
                publicAlbumVo.setDefaultViewPhoto(blogGlobalConfig.getPhotoViewUrl() + albumInstance.getDefaultViewPhoto());
                publicAlbumVos.add(publicAlbumVo);
            }
        }
        log.info("获取模板信息=[{}]", albumTemplateVos);
        mv.addObject("albumTemplates", albumTemplateVos);
        log.info("获取用户=[{}]的相册信息=[{}]", userName, userAlbumVos);
        mv.addObject("userAlbums", userAlbumVos);
        log.info("获取最新最热的公众相册=[{}]", publicAlbumVos);
        mv.addObject("publicAlbums", publicAlbumVos);
        mv.setViewName("index.html");

        return mv;
    }
}
