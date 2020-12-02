package com.yenroc.ho.rest.bookmarks;

import com.yenroc.ho.blogic.java.bookmarks.BookMarksService;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksReqt;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksResp;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksRespS01;
import com.yenroc.ho.common.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@Api(value = "BookMarksController")
@RestController
public class BookMarksController {

    private static final Logger log = LoggerFactory.getLogger(BookMarksController.class);


    @Autowired
    private BookMarksService bookMarksService;

    @PostMapping("/bookMarks/query")
    @ApiOperation(value="bookMarks", notes="bookMarks")
    public ResponseEntity<ResponseResult> query(@RequestBody BookMarksReqt reqt) throws Exception {
        log.debug("bookMarks: {}", reqt);
        BookMarksResp result = bookMarksService.bookMarksQuery(reqt);
        return new ResponseEntity<>(ResponseResult.success(result), HttpStatus.OK);
    }

}
