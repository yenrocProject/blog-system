package com.yenroc.ho.blogic.java.fileService;

import com.yenroc.ho.config.FileConfig;
import com.yenroc.ho.mapper.FileInfoDao;
import com.yenroc.ho.mapper.entity.FileInfo;
import com.yenroc.ho.utils.DateUtil;
import com.yenroc.ho.utils.FileUtil;
import com.yenroc.ho.vo.CommonUploadFileVO;
import com.yenroc.ho.vo.CommonUploadResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public abstract class AbstractFileUploadService implements FileUploadService {

    private final Logger log = LoggerFactory.getLogger(AbstractFileUploadService.class);

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
        List<MultipartFile> fileList = multipartRequest.getFiles("file");

        // 创建上传文件的voList
        List<CommonUploadFileVO> uploadFileVOS = new ArrayList<>();

        for (MultipartFile mf : fileList) {
//            if (mf.isEmpty()) { // 允许空文件
//                throw new BizLogicException(new SystemMessage("FILE_NOT_EMPTY_ERROR","上传文件不可为空"));
//            }
            // 文件全名
            String fileOriginalName = mf.getOriginalFilename();
            if (StringUtils.isBlank(fileOriginalName)) {
                continue;
            }
            log.info("获取上传的文件名=[{}]", fileOriginalName);
            // 文件后缀名
            CommonUploadFileVO uploadFileVO = new CommonUploadFileVO();
            uploadFileVO.setFileOriginalName(fileOriginalName);
            if (fileOriginalName.lastIndexOf(".") >= 0) {
                String suffixName = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);
                uploadFileVO.setSuffixName(suffixName);
            }

            // 生成文件uuid
            String fileId = UUID.randomUUID().toString();
            uploadFileVO.setFileId(fileId);

            // 生成文件名称
            String newFileName = MessageFormat.format("{0}{1}.{2}",
                    DateUtil.toString(DateUtil.getCurDate(),"yyyyMMddHHmmss"),
                    fileId.substring(0,5),
                    uploadFileVO.getSuffixName());
            uploadFileVO.setFileName(newFileName);
            log.info("生成新的文件名称=[{}]", newFileName);

            uploadFileVO.setFileSize(mf.getSize());
            uploadFileVO.setInputStream(mf.getInputStream());
            String fileType = suffixValidation(uploadFileVO);
            uploadFileVO.setFileType(fileType);

            uploadFileVOS.add(uploadFileVO);
        }

        if (uploadFileVOS.size() > 0) {// 需要上传的文件大于0,进行文件上传, 需要在实现类中吧文件流给关闭
            List<CommonUploadResultVO> uploadResultVOS = null;
            switch (fileConfig.getUploadType()){
                case mongodb:
                    uploadResultVOS = mongodbUpload(uploadFileVOS);
                    break;
                case fileSystem:
                    uploadResultVOS = fileSystemUpload(uploadFileVOS);
                    break;
                case aliyunoss:
                    uploadResultVOS = aliyunossUpload(uploadFileVOS);
                    break;
                case amazons3:
                    uploadResultVOS = amazons3Upload(uploadFileVOS);
                    break;
                case ftp:
                    uploadResultVOS = ftpUpload(uploadFileVOS);
                    break;
                default:
                    log.error("文件上传的配置类型未实现,无法进行文件上传!");
            }
            if (!uploadResultVOS.isEmpty()) {
                for (CommonUploadResultVO uploadResultVO : uploadResultVOS) {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setId(uploadResultVO.getFileId());// 文件Id
                    fileInfo.setFileName(uploadResultVO.getFileName()); // 文件名
                    fileInfo.setFileFullPath(uploadResultVO.getFileFullPath());// 文件全路径
                    fileInfo.setThumbFullPath(uploadResultVO.getThumbFullPath());
                    fileInfo.setFileOriginalName(uploadResultVO.getFileOriginalName());
                    fileInfo.setFileSize(uploadResultVO.getFileSize());
                    int insertResult = fileInfoDao.insert(fileInfo);
                    log.info("文件上传数据记录成功=[{}]", insertResult);
                }
                // 返回第一个文件的文件名
                return uploadResultVOS.get(0).getFileName();
            }
        }
        return null;
    }

    /**
     * 进行文件名后缀名称真实校验
     * @param uploadFileVO 文件的信息
     * @return 返回文件的后缀名
     */
    protected abstract String suffixValidation(CommonUploadFileVO uploadFileVO) throws Exception;


    /**
     *
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    protected abstract List<CommonUploadResultVO> mongodbUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception;

    /**
     *
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    protected abstract List<CommonUploadResultVO> fileSystemUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception;

    /**
     *
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    protected abstract List<CommonUploadResultVO> aliyunossUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception;

    /**
     *
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    protected abstract List<CommonUploadResultVO> amazons3Upload(List<CommonUploadFileVO> uploadFileVOS) throws Exception;

    /**
     *
     * @param uploadFileVOS
     * @return
     * @throws Exception
     */
    protected abstract List<CommonUploadResultVO> ftpUpload(List<CommonUploadFileVO> uploadFileVOS) throws Exception;

}
