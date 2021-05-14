package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.album.ViewAlbumService;
import com.yenroc.ho.blogic.java.album.UserAlbumInfoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Api(value = "AlbumController")
@Controller
public class AlbumController {

    private static final Logger log = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private UserAlbumInfoService userAlbumInfoService;


    @Autowired
    private ViewAlbumService lookAlbumService;


    @RequestMapping("/{userName}/index.html")
    public ModelAndView showAlbums(@PathVariable("userName") @NotBlank String userName) throws Exception {
        log.info("REST 进入showAlbums接口,加载加载用户=[{}]相册信息,以及模板信息", userName);
        ModelAndView mv = userAlbumInfoService.showAlbums(userName);
        return mv;
    }

    @RequestMapping("/{userName}/{tempId}/{tempInstId}.html")
    public ModelAndView viewUserAlbum(@PathVariable("userName") String userName,
                                      @PathVariable("tempId") Integer tempId,
                                      @PathVariable("tempInstId") Integer tempInstId, HttpServletRequest request) throws Exception {
        log.info("REST 进入viewUserAlbum接口,用户=[{}]查看模板相册=[{}]相册实例[{}]", userName, tempId, tempInstId);
        String privateKey = request.getParameter(AlbumConsts.ALBUM_KEY);// 相册的key
        ModelAndView mv = lookAlbumService.viewUserAlbum(userName, tempId, tempInstId, privateKey);
        return mv;
    }

    @RequestMapping("/preview/{tempId}.html")
    public ModelAndView previewAlbum(@PathVariable("tempId") Integer tempId) throws Exception {
        log.info("REST 进入previewAlbum接口查看模板相册=[{}]", tempId);
        ModelAndView mv = lookAlbumService.previewAlbum(tempId);
        return mv;
    }

}
