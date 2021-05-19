package com.yenroc.ho.blogic.java.album.impl;

import com.yenroc.ho.blogic.java.album.EditAlbumService;
import com.yenroc.ho.blogic.restDto.album.editAlbum.AlbumStyleCssVo;
import com.yenroc.ho.blogic.restDto.album.editAlbum.EditAlbumPhotoVo;
import com.yenroc.ho.blogic.restDto.album.editAlbum.EditAlbumVo;
import com.yenroc.ho.blogic.restDto.album.viewAlbum.AlbumPhotoInfoVo;
import com.yenroc.ho.blogic.sqlDto.albumPhotoInstance.AlbumPhotoInfo;
import com.yenroc.ho.config.BlogGlobalConfig;
import com.yenroc.ho.mapper.*;
import com.yenroc.ho.mapper.entity.*;
import com.yenroc.ho.utils.BeanCopierEx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class EditAlbumServiceImpl implements EditAlbumService {

    private static final Logger log = LoggerFactory.getLogger(EditAlbumServiceImpl.class);

    @Autowired
    private AlbumTemplateDao albumTemplateDao;

    @Autowired
    private AlbumTemplatePhotoConfigDao albumTemplatePhotoConfigDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AlbumInstanceDao albumInstanceDao;

    @Autowired
    private AlbumPhotoInstanceDao albumPhotoInstanceDao;

    @Autowired
    private AlbumStyleCssDao albumStyleCssDao;

    @Autowired
    private BlogGlobalConfig blogGlobalConfig;

    /**
     * 进入模板相册
     * @param userName
     * @param templateId
     * @return
     */
    public ModelAndView editAlbum(String userName, Integer templateId) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userName", userName);
        EditAlbumVo editAlbumVo = new EditAlbumVo();

        List<User> users = userDao.finByUserName(userName);
        if (users.size() == 0) {
            mv.addObject("errorMsg","用户信息账号不存在,请先创建账号~~");
            mv.setViewName("error.html");
            return mv;
        }
        Integer userId = users.get(0).getId();
        // 查询模板信息
        AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(templateId);
        // 查询模板对应的照片信息
        if (albumTemplate == null) {
            mv.addObject("errorMsg","相册模板未定义配置,暂时不支持创建相册~~");
            mv.setViewName("error.html");
            return mv;
        }
        editAlbumVo.setAlbumTemplateId(albumTemplate.getId());
        editAlbumVo.setUserId(userId);
        editAlbumVo.setAlbumName(albumTemplate.getTemplateName());
        editAlbumVo.setAlbumDesc(albumTemplate.getTemplateDesc());
        editAlbumVo.setPrivateView(0);
        editAlbumVo.setTemplatePhotoSize(albumTemplate.getTemplatePhotoSize());
        editAlbumVo.setAlbumStyleCss(albumTemplate.getTemplateStyleCss());

        String defaultViewPhoto = albumInstanceDao.selectByPrimaryKey(albumTemplate.getDefaultInstanceId()).getDefaultViewPhoto();
        editAlbumVo.setDefaultViewPhoto(defaultViewPhoto);

        // 获取相册配置信息
        List<AlbumTemplatePhotoConfig> photoConfigList = albumTemplatePhotoConfigDao.findByTemplateId(albumTemplate.getId());
        if (photoConfigList.size() > 0) {
            List<EditAlbumPhotoVo> photoVoList = new ArrayList<>();
            for (AlbumTemplatePhotoConfig photoConfig : photoConfigList) {
                EditAlbumPhotoVo editAlbumPhotoVo = new EditAlbumPhotoVo();
                editAlbumPhotoVo.setPhotoConfigName(photoConfig.getPhotoConfigName());
                editAlbumPhotoVo.setPhotoDesc(photoConfig.getPhotoDesc());
                editAlbumPhotoVo.setPhotoWidth(photoConfig.getPhotoWidth());
                editAlbumPhotoVo.setPhotoWidth(photoConfig.getPhotoWidth());
                photoVoList.add(editAlbumPhotoVo);
            }
            editAlbumVo.setAlbumPhotoVoList(photoVoList);
        }
        log.info("获取模板得配置信息结果=[{}]", editAlbumVo);
        mv.addObject("albumInfo", editAlbumVo);

        List<AlbumStyleCss> albumStyleCsses = albumStyleCssDao.selectAll();
        if (albumStyleCsses.size() > 0) {
            ArrayList<AlbumStyleCssVo> albumStyleCssVos = BeanCopierEx.copy(albumStyleCsses, AlbumStyleCssVo.class);
            mv.addObject("albumStyleCssList", albumStyleCssVos);
        }

        mv.setViewName("edit.html");
        return mv;
    }

    /**
     * 编辑模板相册
     * @param userName
     * @param templateId
     * @return
     */
    public ModelAndView editAlbum(String userName, Integer templateId, Integer albumId) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("userName", "defalut");
        List<User> users = userDao.finByUserName(userName);
        if (users.size() == 0) {
            mv.addObject("errorMsg","用户信息账号不存在,请先创建账号~~");
            mv.setViewName("error.html");
            return mv;
        }
        Integer userId = users.get(0).getId();
        AlbumInstance albumInstance = albumInstanceDao.selectByPrimaryKey(albumId);
        // 查询对应的照片信息
        if (albumInstance == null || !albumInstance.getUserId().equals(userId)) {
            log.warn("当前相册实例和登录人不一致...");
            mv.addObject("errorMsg","当前访问异常(当前相册实例和登录人不一致,不支持编辑)~~");
            mv.setViewName("error.html");
            return mv;
        }
        // 查询模板信息
        AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(templateId);
        // 查询模板对应的照片信息
        if (albumTemplate == null) {
            mv.addObject("errorMsg","相册模板未定义配置,暂时不支持创建相册~~");
            mv.setViewName("error.html");
            return mv;
        }

        EditAlbumVo editAlbumVo = new EditAlbumVo();
        editAlbumVo.setId(albumInstance.getId());
        editAlbumVo.setAlbumTemplateId(albumInstance.getAlbumTemplateId());
        editAlbumVo.setUserId(userId);
        editAlbumVo.setAlbumName(albumInstance.getAlbumName());
        editAlbumVo.setAlbumDesc(albumInstance.getAlbumDesc());
        editAlbumVo.setPrivateView(albumInstance.getPrivateView());
        editAlbumVo.setTemplatePhotoSize(albumTemplate.getTemplatePhotoSize());
        editAlbumVo.setAlbumStyleCss(albumInstance.getAlbumStyleCss());
        editAlbumVo.setDefaultViewPhoto(albumInstance.getDefaultViewPhoto());
        editAlbumVo.setMusicId(albumInstance.getMusicId());
        editAlbumVo.setMusicName(albumInstance.getMusicName());
        // 加载 用户得照片信息
        List<AlbumPhotoInfo> albumPhotoInfoList = albumPhotoInstanceDao.getPhotoInfoByAlbumId(albumInstance.getId());

        if (albumPhotoInfoList.size() > 0) {
            List<EditAlbumPhotoVo> photoVoList = new ArrayList<>();
            for (AlbumPhotoInfo albumPhotoInfo : albumPhotoInfoList) {
                EditAlbumPhotoVo editAlbumPhotoVo = new EditAlbumPhotoVo();
                editAlbumPhotoVo.setPhotoConfigName(albumPhotoInfo.getPhotoConfigName());
                editAlbumPhotoVo.setPhotoDesc(albumPhotoInfo.getPhotoDesc());
                editAlbumPhotoVo.setPhotoWidth(albumPhotoInfo.getPhotoWidth());
                editAlbumPhotoVo.setPhotoWidth(albumPhotoInfo.getPhotoWidth());
                editAlbumPhotoVo.setId(albumPhotoInfo.getAlbumPhotoInstanceId());
                // 通过文件服务器提供的预览,可使用nginx
                if (StringUtils.isNotBlank(albumPhotoInfo.getFileName())) {
                    editAlbumPhotoVo.setFileId(albumPhotoInfo.getFileId());
                    editAlbumPhotoVo.setFileName(albumPhotoInfo.getFileName());
                    editAlbumPhotoVo.setPhotoUrl(blogGlobalConfig.getPhotoViewUrl() + albumPhotoInfo.getPhotoUrl());
                    editAlbumPhotoVo.setPhotoUrl(StringUtils.replace(editAlbumPhotoVo.getPhotoUrl(),"\\","/"));

                }
                photoVoList.add(editAlbumPhotoVo);
            }
            photoVoList = photoVoList.stream().sorted(Comparator.comparing(EditAlbumPhotoVo::getId)).collect(Collectors.toList());
            editAlbumVo.setAlbumPhotoVoList(photoVoList);
        }
        log.info("获取相册实例信息结果=[{}]", editAlbumVo);
        mv.addObject("albumInfo", editAlbumVo);
        List<AlbumStyleCss> albumStyleCsses = albumStyleCssDao.selectAll();
        if (albumStyleCsses.size() > 0) {
            ArrayList<AlbumStyleCssVo> albumStyleCssVos = BeanCopierEx.copy(albumStyleCsses, AlbumStyleCssVo.class);
            mv.addObject("albumStyleCssList", albumStyleCssVos);
        }

        mv.setViewName("edit.html");
        return mv;
    }

}
