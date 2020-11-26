package com.yenroc.ho.utils;

import com.yenroc.ho.vo.CommonUploadFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;

/**
 * 文件操作的工具类
 * @author: 和彦鹏
 * @date: 2020年11月24日
 * @version: v1.0
 */
public class FileUtil {

    private final static Logger log = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 描述：对InputStream进行缓存操作 因为一个InputStream只能进行一次读取，多次读取需要进行转换
     */
    public static ByteArrayOutputStream getCopyInputStream(CommonUploadFileVO commonUploadFileVO) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = commonUploadFileVO.getInputStream().read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            // 将数据流重置回去
            commonUploadFileVO.setInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return baos;
        }
        catch(Exception ex) {
            log.error(MessageFormat.format("复制输入流时出现错误：{0}", commonUploadFileVO.toString()));
            throw ex;
        }
    }

    /**
     * 文件上传
     * @param inputStream
     * @param fileName
     * @param uploadPath
     * @throws Exception
     */
    public static void uploadFile(InputStream inputStream, String uploadPath, String fileName) throws Exception {
        OutputStream os = null;
        try {
            File file = new File(uploadPath + File.separator + fileName);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e) {
            log.error("上传文件失败", e);
            throw e;
        }
        finally {
            if(os != null) {
                os.close();
            }
        }
    }

}
