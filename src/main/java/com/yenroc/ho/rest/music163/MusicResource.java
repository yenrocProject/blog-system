package com.yenroc.ho.rest.music163;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginReqt;
import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginResp;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.rest.album.AlbumResource;
import com.yenroc.ho.rest.music163.music.MusicResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.net.URLEncoder;

@RequestMapping("/api")
@Api(value = "MusicResource")
@RestController
public class MusicResource {

    private static final Logger log = LoggerFactory.getLogger(MusicResource.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/music163/queryMusicName")
    @ApiOperation(value="获取音乐信息列表", notes="获取音乐信息列表")
    public ResponseEntity<ResponseResult> queryMusicName(HttpServletRequest request) throws Exception {
        String musicName = request.getParameter("musicName");
        log.info("REST 获取音乐信息列表,音乐名称入参=[{}]", musicName);
        String musicNameEncode = URLEncoder.encode(musicName, "utf-8").replaceAll("\\+", "");
        URL url = new URL("http://music.163.com/api/search/pc?s=" + musicNameEncode + "&type=1");
        String musicRespStr = restTemplate.getForObject(url.toURI(), String.class);
        MusicResp musicResp = objectMapper.readValue(musicRespStr, MusicResp.class);
        return new ResponseEntity<>(ResponseResult.success(musicResp), HttpStatus.OK);
    }
}
