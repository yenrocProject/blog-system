package com.yenroc.ho.blogic.java.album;

import org.springframework.web.servlet.ModelAndView;

public interface UserAlbumInfoService {

    /**
     * 根据用户名:加载用户相册信息,以及模板信息
     * @param userName
     * @return
     */
    ModelAndView showAlbums(String userName);

}
