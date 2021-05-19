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
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;

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

        Enumeration headerNames = request.getHeaderNames();
        HttpHeaders header = new HttpHeaders();
        //使用循环遍历请求头，并通过getHeader()方法获取一个指定名称的头字段
//        while (headerNames.hasMoreElements()) {
//            String headerName = (String) headerNames.nextElement();
//            header.add(headerName, request.getHeader(headerName));
//        };
        header.add("Accept","application/json, text/plain, */*");
        header.add("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        header.add("Referer","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        header.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36 Edg/90.0.818.62");


//        String musicNameEncode = URLEncoder.encode(musicName, "utf-8").replaceAll("\\+", "");
        String url = "http://music.163.com/api/search/pc?s=" + musicName + "&type=1&a="+Thread.currentThread().getName();
        log.info(url);
//        String musicRespStr = restTemplate.getForObject(url, String.class);

        HttpEntity httpEntity = new HttpEntity(header);
// 构造execute()执行所需要的参数。
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpEntity, String.class);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
// 执行execute()，发送请求
        ResponseEntity<String> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
        String musicRespStr = responseEntity.getBody();
        log.info("REST 获取音乐信息列表返回结果=[{}]", musicRespStr);
        MusicResp musicResp = objectMapper.readValue(musicRespStr, MusicResp.class);
        log.info("获取音乐信息列表 结果={}", musicResp);
        return new ResponseEntity<>(ResponseResult.success(musicResp), HttpStatus.OK);
    }
}
