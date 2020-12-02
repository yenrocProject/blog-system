package com.yenroc.ho.blogic.java.fileService;

import com.yenroc.ho.blogic.java.fileService.constant.CommonFileConstant;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.config.FileConfig;
import com.yenroc.ho.mapper.FileInfoDao;
import com.yenroc.ho.mapper.entity.FileInfo;
import com.yenroc.ho.utils.AsposeUtil;
import com.yenroc.ho.utils.FileUtil;
import com.yenroc.ho.vo.CommonUploadFileVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.detect.AutoDetectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;

/**
 * 描述:通用的文件下载预览机能
 * 1.从fileName 获取文件信息
 * 2.根据文件信息读取url
 * 3.获取文件,设置response
 * @version:1.0.0
 * @author: 和彦鹏
 * @date:2020年11月30日
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractFileDownloadService implements FileDownloadService {

    private final Logger log = LoggerFactory.getLogger(AbstractFileDownloadService.class);

    public static final String DEFAULT_MIMETYPE = "application/octet-stream";


    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private AsposeUtil asposeUtil;

    @Override
    public void download(String fileName, HttpServletResponse response) throws Exception {
        this.download(fileName,response,false,false,false);
    }

    @Override
    public void downloadStream(String fileName, HttpServletResponse response) throws Exception {
        this.download(fileName,response, true, false, false);
    }

    @Override
    public void preview(String fileName, HttpServletResponse response) throws Exception {
        this.download(fileName,response, true, true, false);
    }

    @Override
    public void previewWithWatermark(String fileName, HttpServletResponse response) throws Exception {
        this.download(fileName,response, true, true, true);
    }

    /**
     * 文件下载预览
     * @param fileName 文件名
     * @param response 响应体
     * @param downloadStream 是否以流的形式下载
     * @param ifPreview 是否预览
     * @param ifAddWatermark 是否添加水印
     * @throws Exception
     */
    public void download(String fileName, HttpServletResponse response, Boolean downloadStream, boolean ifPreview,
                  boolean ifAddWatermark) throws Exception {
        // 1. 进行权限验证
        downloadAuth(fileName, response);

        // 2. 获取文件的信息
        FileInfo fileInfo = fileInfoDao.findByFileName(fileName);
        if (null == fileInfo || "".equals(fileInfo.getId())) {
            throw new BizLogicException(new SystemMessage("FILE_NOT_FOUNT", MessageFormat.format("文件=[{0}]没有找到！", fileName)));
        }

        InputStream inStream = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); // 输出流
        byte[] data = null; // 输出流字节数组
        OutputStream outputStream = null;
        ByteArrayOutputStream copyStream = null;// 为txt类型做复制用的输出流
        ByteArrayInputStream byteArrayCopyInputStream = null;// 为txt类型做的复制用的输入流
        try {
            switch (fileConfig.getUploadType()){
                case mongodb:
                    inStream = mongodbDownload(fileInfo);
                    break;
                case fileSystem:
                    inStream = fileSystemDownload(fileInfo);
                    break;
                case aliyunoss:
                    inStream = aliyunossDownload(fileInfo);
                    break;
                case amazons3:
                    inStream = amazons3Download(fileInfo);
                    break;
                case ftp:
                    inStream = ftpDownload(fileInfo);
                    break;
                default:
                    log.error("文件上传的配置类型未实现,无法进行文件上传!");
                    throw new BizLogicException(new SystemMessage("FILE_DOWNLOAD_EXCEPTION", MessageFormat.format(
                            "文件名=[{0}]进行下载预览!", fileName)));
            }

            // 获取文件的后缀名
            String suffixName = fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(".") + 1);

            // 获取文件的后缀[防止文件乱码]
            String txtCharset = "";
            if (StringUtils.equalsIgnoreCase(CommonFileConstant.TXT, suffixName)) {
                // 如果是txt文件,则获取编码格式
                CommonUploadFileVO commonUploadFileVO = new CommonUploadFileVO();
                commonUploadFileVO.setInputStream(inStream);
                copyStream = FileUtil.getCopyInputStream(commonUploadFileVO);
                byteArrayCopyInputStream = new ByteArrayInputStream(copyStream.toByteArray());
                AutoDetectReader detect =  new AutoDetectReader(byteArrayCopyInputStream);
                txtCharset = detect.getCharset().name();
                log.info("获取文件=[{}]的编码是：{}",fileName, txtCharset);
                // 将数据流重置回去
                inStream = commonUploadFileVO.getInputStream();
            }

            // 如果是预览
            // 针对doc,xls,pdf处理
            if(StringUtils.equalsIgnoreCase(CommonFileConstant.DOC, suffixName)
                    ||StringUtils.equalsIgnoreCase(CommonFileConstant.DOCX, suffixName)){
                outStream = (ByteArrayOutputStream) asposeUtil.wordToPDF(ifAddWatermark,"",inStream);// TODO
                fileInfo.setFileOriginalName(fileInfo.getFileOriginalName().replace(".docx", ".pdf").replace(".doc",".pdf"));

            } else if (StringUtils.equalsIgnoreCase(CommonFileConstant.XLS, suffixName)
                    ||StringUtils.equalsIgnoreCase(CommonFileConstant.XLSX, suffixName)) {
                outStream = (ByteArrayOutputStream) asposeUtil.excelToPDF(ifAddWatermark,"",inStream);
                fileInfo.setFileOriginalName(fileInfo.getFileOriginalName().replace(".xlsx", ".pdf").replace(".xls",
                        ".pdf"));// TODO

            } else if (StringUtils.equalsIgnoreCase(CommonFileConstant.DOC, suffixName)
                    ||StringUtils.equalsIgnoreCase(CommonFileConstant.PDF, suffixName)) {
                outStream = (ByteArrayOutputStream) asposeUtil.doPDF("",inStream);// TODO

            } else {
                byte[] buffer = new byte[8096];
                int len = 0;
                while( (len=inStream.read(buffer)) != -1 ) {
                    outStream.write(buffer, 0, len);
                }
            }
            data = outStream.toByteArray();
            response.reset();// 清空response
            response.setCharacterEncoding("UTF-8");// 设置response的Header
            outputStream = new BufferedOutputStream(response.getOutputStream());
            String contentType = DEFAULT_MIMETYPE;// "application/octet-stream"
            if (StringUtils.isNotBlank(txtCharset)) {
                if(txtCharset.startsWith("UTF")) {
                    contentType = "application/octet-stream;charset=UTF-8";
                } else {
                    contentType = "application/octet-stream;charset=GB2312";
                }
            }
            response.setContentType(contentType);

            // 下载文件, 非流的形式
            if(!downloadStream) {
                String fileNameUtf8 = new String(fileInfo.getFileOriginalName().getBytes("utf-8"), "utf-8");
                fileNameUtf8 = java.net.URLEncoder.encode(fileNameUtf8, "UTF-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileNameUtf8);
                response.addHeader("Content-Length", "" + data.length);
            }

            outputStream.write(data);
            outputStream.flush();
        } catch (Exception ex){
            log.error("文件名=[{}]进行下载预览出错=", fileName, ex);
            throw new BizLogicException(new SystemMessage("FILE_DOWNLOAD_EXCEPTION", MessageFormat.format(
                    "文件名=[{0}]进行下载预览出错={1}", fileName, ex.getMessage())));
        } finally {
            // 进行流关闭,防止内存泄露
            if(inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    // 忽略关闭错误
                }
            }
            if(outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    // 忽略关闭错误
                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // 忽略关闭错误
                }
            }
            if(copyStream != null) {
                try {
                    copyStream.close();
                } catch (IOException e) {
                    // 忽略关闭错误
                }
            }
            if(byteArrayCopyInputStream != null) {
                try {
                    byteArrayCopyInputStream.close();
                } catch (IOException e) {
                    // 忽略关闭错误
                }
            }
        }
    }

    /**
     * 文件下载权限验证, 没有权限直接抛401或重定向
     * @param fileName
     * @return
     * @throws Exception
     */
    protected abstract String downloadAuth(String fileName, HttpServletResponse response) throws Exception;

    /**
     *  mongodbDownload
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream mongodbDownload(FileInfo fileInfo) throws Exception;

    /**
     * fileSystemDownload
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream fileSystemDownload(FileInfo fileInfo) throws Exception;

    /**
     * 阿里云文件下载
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream aliyunossDownload(FileInfo fileInfo) throws Exception;

    /**
     * 亚马逊 Download
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream amazons3Download(FileInfo fileInfo) throws Exception;

    /**
     * ftp Download
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream ftpDownload(FileInfo fileInfo) throws Exception;

    /**
     * default Download [从配置 文件地址+文件名 获取流]
     * @param fileInfo
     * @return
     * @throws Exception
     */
    protected abstract InputStream defaultDownload(FileInfo fileInfo) throws Exception;

}
