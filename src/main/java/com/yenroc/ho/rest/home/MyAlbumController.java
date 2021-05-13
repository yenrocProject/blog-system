package com.yenroc.ho.rest.home;

import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.mapper.AlbumTemplateDao;
import com.yenroc.ho.mapper.AlbumUserDao;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import com.yenroc.ho.mapper.entity.AlbumUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Api(value = "MyAlbumController")
@Controller
public class MyAlbumController {

    private static final Logger log = LoggerFactory.getLogger(MyAlbumController.class);

    @Autowired
    private AlbumTemplateDao albumTemplateDao;


    @RequestMapping("/{userName}/index.html")
    public ModelAndView albumUserCreate(@PathVariable("userName") String userName) throws Exception {
        ModelAndView mv = new ModelAndView();

        AlbumTemplate selectParams = new AlbumTemplate();
        List<AlbumTemplate> albumTemplates = albumTemplateDao.select(selectParams);
        mv.addObject("albumTemplates", albumTemplates);

        mv.addObject("userName", userName);

        mv.setViewName("index.html");
        return mv;
    }

    @RequestMapping("/{userName}/{tempId}/{tempInstId}.html")
    public ModelAndView printView(@PathVariable("userName") String userName,
                                  @PathVariable("tempInstId") String tempInstId) throws Exception {
        ModelAndView mv = new ModelAndView();

        mv.addObject("userName", userName);

        mv.addObject("images", new String[]{"https://www.yenroc.top/album/demo/images/1/1.jpg"});


        mv.setViewName("temp1/index.html");
        return mv;
    }

}
