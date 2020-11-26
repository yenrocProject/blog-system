package com.yenroc.ho.rest.file;

import com.yenroc.ho.blogic.java.fileService.FIleService;
import com.yenroc.ho.blogic.java.fileService.FileUploadService;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:资源上传操作接口
 * @version:1.0.0
 * @author:和彦鹏
 * @date:日期
 */
@RestController
@RequestMapping("/api")
@Api(value="资源上传操作接口", tags = "FileUploadController")
public class FileUploadController {

    private final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * POST  /common/fileUpload/: 附件上传
     *
     * @param request 请求体
     * @return ResponseEntity 200：成功，其他响应表示有问题。
     */
    @PostMapping("/file/upload")
    public ResponseEntity<Object> upload(HttpServletRequest request) throws Exception {
        log.info("文件上传编码：{}", request.getCharacterEncoding());
        //构造上传的MultipartHttpServletRequest
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        try {
            multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        } catch (Exception e) {
            log.error("获取文件信息出现错误", e);
            throw new BizLogicException(new SystemMessage("GET_FILE_ERROR","获取文件信息出现错误"));
        }
        //调用上传Service
        String fileUrl = fileUploadService.upload(multipartHttpServletRequest);
        return new ResponseEntity<>(ResponseResult.success(fileUrl), HttpStatus.OK);
    }

}
