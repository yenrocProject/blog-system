package com.yenroc.ho.rest.album;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.album.EditAlbumService;
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

    @Autowired
    private EditAlbumService editAlbumService;

    /**
     * 主页
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping("/{userName}/index.html")
    public ModelAndView showAlbums(@PathVariable("userName") @NotBlank String userName) throws Exception {
        log.info("REST 进入showAlbums接口,加载加载用户=[{}]相册信息,以及模板信息", userName);
        ModelAndView mv = userAlbumInfoService.showAlbums(userName);
        return mv;
    }

    /**
     * 查看用户相册
     * @param userName
     * @param tempId
     * @param tempInstId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/{userName}/{tempId}/{tempInstId}.html")
    public ModelAndView viewUserAlbum(@PathVariable("userName") String userName,
                                      @PathVariable("tempId") Integer tempId,
                                      @PathVariable("tempInstId") Integer tempInstId, HttpServletRequest request) throws Exception {
        log.info("REST 进入viewUserAlbum接口,用户=[{}]查看模板相册=[{}]相册实例[{}]", userName, tempId, tempInstId);
        String privateKey = request.getParameter(AlbumConsts.ALBUM_KEY);// 相册的key
        ModelAndView mv = lookAlbumService.viewUserAlbum(userName, tempId, tempInstId, privateKey);
        return mv;
    }

    /**
     * 预览模板
     * @param tempId
     * @return
     * @throws Exception
     */
    @RequestMapping("/preview/{tempId}.html")
    public ModelAndView previewAlbum(@PathVariable("tempId") Integer tempId) throws Exception {
        log.info("REST 进入previewAlbum接口查看模板相册=[{}]", tempId);
        ModelAndView mv = lookAlbumService.previewAlbum(tempId);
        return mv;
    }

    /**
     * 编辑模板
     * @param tempId
     * @return
     * @throws Exception
     */
    @RequestMapping("/{userName}/edit/{templateId}.html")
    public ModelAndView editAlbum(@PathVariable("userName") String userName,
                                    @PathVariable("templateId") Integer tempId) throws Exception {
        log.info("REST 用户=[{}]进行模板=[{}]的相册创建", userName, tempId);
        ModelAndView mv = editAlbumService.editAlbum(userName, tempId);
        return mv;
    }

    /**
     * 编辑模板
     * @param tempId
     * @return
     * @throws Exception
     */
    @RequestMapping("/{userName}/edit/{templateId}/{albumId}.html")
    public ModelAndView editAlbum(@PathVariable("userName") String userName,
                                  @PathVariable("templateId") Integer tempId,
                                  @PathVariable("albumId") Integer albumId) throws Exception {
        log.info("REST 用户=[{}]进行相册=[{}]的相册编辑", userName, albumId);
        ModelAndView mv = editAlbumService.editAlbum(userName, tempId, albumId);
        return mv;
    }

}
