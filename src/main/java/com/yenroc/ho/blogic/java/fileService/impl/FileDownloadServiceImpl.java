package com.yenroc.ho.blogic.java.fileService.impl;

import com.yenroc.ho.blogic.java.fileService.AbstractFileDownloadService;
import com.yenroc.ho.blogic.java.fileService.FileDownloadService;
import com.yenroc.ho.mapper.entity.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

@Slf4j
@Service
public class FileDownloadServiceImpl extends AbstractFileDownloadService implements FileDownloadService {

    @Override
    protected String downloadAuth(String fileName, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    protected InputStream mongodbDownload(FileInfo fileInfo) throws Exception {
        return null;
    }

    @Override
    protected InputStream fileSystemDownload(FileInfo fileInfo) throws Exception {
        // 创建URL
        URL url = new URL(fileInfo.getFileFullPath());
        // 如果是https请求，则用https去调用
        log.info("文件地址[{}],协议：[{}]", url.getPath(), url.getProtocol());
        if("https".equals(url.getProtocol())) {
            // 创建链接
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为协议,第二个参数为提供者(可以缺省)
//                TrustManager[] tm = {new PaiX509TrustManager()};
                sslContext.init(null, null, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                conn.setSSLSocketFactory(ssf);
            } catch (Exception e) {
                log.error("从[{}]获取数据流出现错误，错误消息为：{}", url.getPath(), e.getMessage(), e);
                throw e;
            }
            conn.setRequestMethod("GET");
            // 超时时间15秒
            conn.setConnectTimeout(150 * 1000);
            return conn.getInputStream();
        }
        else {
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 超时时间15秒
            conn.setConnectTimeout(150 * 1000);
            return conn.getInputStream();
        }
    }

    @Override
    protected InputStream aliyunossDownload(FileInfo fileInfo) throws Exception {
        return null;
    }

    @Override
    protected InputStream amazons3Download(FileInfo fileInfo) throws Exception {
        return null;
    }

    @Override
    protected InputStream ftpDownload(FileInfo fileInfo) throws Exception {
        return null;
    }

    @Override
    protected InputStream defaultDownload(FileInfo fileInfo) throws Exception {
        return null;
    }
}
