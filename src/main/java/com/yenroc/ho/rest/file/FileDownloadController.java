package com.yenroc.ho.rest.file;

import com.yenroc.ho.blogic.java.fileService.FileDownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述:资源下载操作接口
 * @version:1.0.0
 * @author:和彦鹏
 * @date:日期
 */
@RestController
@RequestMapping("/api")
@Api(value="资源下载操作接口", tags = "FileDownloadController")
public class FileDownloadController {

    private final Logger log = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    private FileDownloadService fileDownloadService;

    /**
     * GET  /file/download: 附件下载
     *
     * @param response 响应体
     * @return ResponseEntity 200：成功，其他响应表示有问题。
     */
    @GetMapping("/file/download/**/{fileName}")
    public void Download(@ApiParam(value="文件名", required = true) @PathVariable String fileName
            , HttpServletResponse response) throws Exception {
        log.info("开始下载文件=[{}]", fileName);
        fileDownloadService.download(fileName, response);
    }

    /**
     * GET  /file/downloadStream: 附件下载, 所有格式文件均做在线数据流
     * @param response 响应体
     * @return ResponseEntity 200：成功，其他响应表示有问题。
     */
    @GetMapping("/file/downloadStream/**/{fileName}")
    public void DownloadStream(@ApiParam(value="文件名", required = true) @PathVariable String fileName
            , HttpServletResponse response) throws Exception {
        log.info("开始下载文件=[{}]", fileName);
        fileDownloadService.downloadStream(fileName, response);
    }

    /**
     * 图片格式/pdf/excel/word 在线数据流
     *
     * @param response 响应体
     * @return ResponseEntity 200：成功，其他响应表示有问题。
     */
    @GetMapping("/file/preview/**/{fileName}")
    public void preview(@ApiParam(value="旧图片id", required = true) @PathVariable String fileName
            , HttpServletResponse response) throws Exception {
        log.info("开始下载文件=[{}]", fileName);
        fileDownloadService.preview(fileName, response);
    }

    /**
     * 图片格式/pdf/excel/word 做在线数据流并添加水印
     *
     * @param response 响应体
     * @return ResponseEntity 200：成功，其他响应表示有问题。
     */
    @GetMapping("/file/previewWithWatermark/**/{fileName}")
    public void previewWithWatermark(@ApiParam(value="旧图片id", required = true) @PathVariable String fileName
            , HttpServletResponse response) throws Exception {
        log.info("开始下载文件=[{}]", fileName);
        fileDownloadService.previewWithWatermark(fileName, response);
    }
}
