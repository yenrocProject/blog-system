package com.yenroc.ho.blogic.java.album;

import org.springframework.web.servlet.ModelAndView;

public interface ViewAlbumService {

    /**
     * 预览模板相册
     * @param templateId
     * @return
     */
    ModelAndView previewAlbum(Integer templateId);

    /**
     * 查看用户相册
     * @param userName
     * @param templateId
     * @param albumId
     * @return
     */
    ModelAndView viewUserAlbum(String userName, Integer templateId, Integer albumId, String privateKey);


}
