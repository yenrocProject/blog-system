package com.yenroc.ho.blogic.java.fileService.impl;

import com.yenroc.ho.blogic.java.fileService.AbstractFileUploadService;
import com.yenroc.ho.blogic.java.fileService.FileUploadService;
import com.yenroc.ho.blogic.java.fileService.PicsAndVideos;
import com.yenroc.ho.blogic.java.fileService.constant.CommonFileConstant;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.config.FileConfig;
import com.yenroc.ho.utils.DateUtil;
import com.yenroc.ho.utils.FileUtil;
import com.yenroc.ho.vo.CommonUploadFileVO;
import com.yenroc.ho.vo.CommonUploadResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:通用的文件上传机能
 * 1.从multipartRequest中解析出CommonUploadFileVO的数组
 * 2.根据上传配置文件解析调用不用的文件上传接口进行上传
 * 3.如果是图片则需要进行缩略图解析并上传
 * @version:1.0.0
 * @author: 和彦鹏
 * @date:2020年11月24日
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class FileUploadServiceImpl extends AbstractFileUploadService implements FileUploadService {

    private final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private PicsAndVideos picsAndVideos;

    /**
     * 文件上传
     * @param multipartRequest
     * @return
     * @throws Exception
     */
    @Override
    public String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
        return super.upload(multipartRequest);
    }

    @Override
    protected String suffixValidation(CommonUploadFileVO uploadFileVO) throws Exception {
        // 如果需要验证,需要拷贝InputStream 来使用!

        if (uploadFileVO.getFileOriginalName().lastIndexOf(".") >= 0) {// 这里不做真实后缀名验证.
            return uploadFileVO.getFileOriginalName().substring(uploadFileVO.getFileOriginalName().lastIndexOf(".") + 1);
        }
        return null;
    }

    @Override
    protected List<CommonUploadResultVO> mongodbUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception {
        log.info("进行mongodbUpload文件上传");
        return null;
    }

    /**
     * 文件系统上传
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    @Override
    protected List<CommonUploadResultVO> fileSystemUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception {
        log.info("进行fileSystemUpload文件上传");
        String folder = DateUtil.toString(DateUtil.getCurDate(),"yyyyMMdd");
        String fullFolderPath = MessageFormat.format("{0}{1}{2}",
                fileConfig.getUploadPath(),
                File.separator,
                folder);
        log.info("上传文件的路径=[{}]", fullFolderPath);
        File f1 = new File(fullFolderPath);
        if (!f1.exists()) {
            if(f1.mkdirs()){
                log.info("上传文件的路径=[{}]创建成功", fullFolderPath);
            }
        }

        List<CommonUploadResultVO> result = new ArrayList<>();
        for (CommonUploadFileVO uploadFileVO : uploadFileVOS) {
            StopWatch stopwatch = new StopWatch();

            log.info("开始上传文件=[{}]", uploadFileVO);
            InputStream thumbInputStream = null;
            String thumbFileName = "";
            try {
                thumbInputStream = picsAndVideos.getThumbnailStream(uploadFileVO);

                // 文件上传
                stopwatch.start();
                FileUtil.uploadFile(uploadFileVO.getInputStream(), fullFolderPath, uploadFileVO.getFileName());
                stopwatch.stop();
                log.info("文件=[{}]上传完成,耗时=[{}ms]", uploadFileVO.getFileOriginalName(), stopwatch.getTotalTimeMillis());
                if (null != thumbInputStream) {
                    // 缩略图上传
                    thumbFileName =
                            uploadFileVO.getFileName().replace("." + uploadFileVO.getSuffixName(), "") + CommonFileConstant.THUMBNAIL + "." +uploadFileVO.getSuffixName();
                    stopwatch.start();
                    FileUtil.uploadFile(thumbInputStream, fullFolderPath, thumbFileName);
                    stopwatch.stop();
                    log.info("文件=[{}]缩略图上传完成,耗时=[{}ms]", uploadFileVO.getFileOriginalName(),
                            stopwatch.getTotalTimeMillis());
                }
                // 返回上传的数据
                CommonUploadResultVO uploadResultVO = new CommonUploadResultVO();
                uploadResultVO.setFileId(uploadFileVO.getFileId());
                uploadResultVO.setFileName(uploadFileVO.getFileName());
                uploadResultVO.setFileSize(uploadFileVO.getFileSize());
                uploadResultVO.setFileOriginalName(uploadFileVO.getFileOriginalName());
                uploadResultVO.setFileFullPath(File.separator + folder + File.separator + uploadFileVO.getFileName());
                uploadResultVO.setThumbFullPath(File.separator + folder + File.separator + thumbFileName);
                result.add(uploadResultVO);
            } catch (Exception ex) {
                log.info("文件上传异常=", ex);
                throw new BizLogicException(new SystemMessage("FILE_UPLOAD_EXCEPTION","文件上传异常="+ex.getMessage()));
            } finally {
                if (thumbInputStream != null) {
                    thumbInputStream.close();
                }
                uploadFileVO.getInputStream().close();
            }
        }
        return result;
    }

    @Override
    protected List<CommonUploadResultVO> aliyunossUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception {
        log.info("进行aliyunossUpload文件上传");
        return null;
    }

    @Override
    protected List<CommonUploadResultVO> amazons3Upload(List<CommonUploadFileVO> uploadFileVOS) throws Exception {
        log.info("进行amazons3Upload文件上传");
        return null;
    }

    @Override
    protected List<CommonUploadResultVO> ftpUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception {
        log.info("进行ftpUpload文件上传");
        return null;
    }
}
