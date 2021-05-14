package com.yenroc.ho.blogic.java.album.impl;

import com.yenroc.ho.blogic.java.album.UserAlbumInfoService;
import com.yenroc.ho.blogic.restDto.album.userAlbumInfo.AlbumVo;
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

    public ModelAndView showAlbums(String userName) {
        ModelAndView mv = new ModelAndView();

        List<User> users = userDao.finByUserName(userName);
        if (users.size() == 0) {
            mv.addObject("errorMsg","页面找不到了~~");
            mv.setViewName("404.html");
            return mv;
        }
        User user = users.get(0);

        List<AlbumTemplate> albumTemplates = albumTemplateDao.selectAll();
        List<AlbumInstance> albumInstances = albumInstanceDao.finByUserId(user.getId());

        List<AlbumVo> albumVos = new ArrayList<>();
        if (albumTemplates.size() > 0) {
            for (AlbumTemplate albumTemplate : albumTemplates) {
                AlbumVo albumVo = new AlbumVo();
                albumVo.setTemplateId(albumTemplate.getId());
                albumVo.setTemplateName(albumTemplate.getTemplateName());
                albumVo.setTemplateDesc(albumTemplate.getTemplateDesc());
                albumVo.setDefaultInstanceId(albumTemplate.getDefaultInstanceId());
                albumVo.setTemplatePhotoSize(albumTemplate.getTemplatePhotoSize());
                if (albumInstances.size() > 0) {
                    for ( AlbumInstance albumInstance : albumInstances) {
                        albumVo.setUserAlbumId(albumInstance.getId());
                        albumVo.setAlbumName(albumInstance.getAlbumName());
                        albumVo.setAlbumDesc(albumInstance.getAlbumDesc());
                        albumVo.setPrivateKey(albumInstance.getPrivateKey());
                    }
                }
                albumVos.add(albumVo);
            }
        }
        log.info("获取albums=[{}]", albumVos);
        mv.addObject("albums", albumVos);
        mv.addObject("userName", userName);
        mv.setViewName("index.html");

        return mv;
    }
}
