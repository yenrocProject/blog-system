package com.yenroc.ho.blogic.java.fileService;

import javax.servlet.http.HttpServletResponse;

public interface FileDownloadService {

    /**
     * 文件下载
     * @param fileName 文件名
     * @param  response 响应体
     * @return
     */
    void download(String fileName, HttpServletResponse response) throws Exception;

    /**
     * 文件下载 [以流的形式下载]
     * @param fileName 文件名
     * @param  response 响应体
     * @return
     */
    void downloadStream(String fileName, HttpServletResponse response) throws Exception;


    /**
     * 文件预览
     * @param fileName 文件名
     * @param  response 响应体
     * @return
     */
    void preview(String fileName, HttpServletResponse response) throws Exception;


    /**
     * 文件加水印预览
     * @param fileName 文件名
     * @param  response 响应体
     * @return
     */
    void previewWithWatermark(String fileName, HttpServletResponse response) throws Exception;

}
