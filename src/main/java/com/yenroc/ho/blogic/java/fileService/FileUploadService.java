package com.yenroc.ho.blogic.java.fileService;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUploadService {

    /**
     * 文件上传
     * @param multipartHttpServletRequest
     * @return 上传成功的url路径
     */
    String upload(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

}
