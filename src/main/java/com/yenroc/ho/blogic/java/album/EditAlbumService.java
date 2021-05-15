package com.yenroc.ho.blogic.java.album;

import org.springframework.web.servlet.ModelAndView;

public interface EditAlbumService {

    /**
     * 编辑模板相册
     * @param userName
     * @param templateId
     * @return
     */
    ModelAndView editAlbum(String userName, Integer templateId);

    /**
     * 创建模板相册
     * @param userName
     * @param templateId
     * @return
     */
    ModelAndView editAlbum(String userName, Integer templateId, Integer albumId);

}
