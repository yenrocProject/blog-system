package com.yenroc.ho.blogic.java.fileService;

import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.config.FileConfig;
import com.yenroc.ho.mapper.FileInfoDao;
import com.yenroc.ho.mapper.entity.FileInfo;
import com.yenroc.ho.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;

@Service("FIleService")
@Transactional(rollbackFor = Exception.class)
public class FIleService {

    private static final Logger log = LoggerFactory.getLogger(FIleService.class);

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private FileInfoDao fileInfoDao;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    public String upload(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BizLogicException(new SystemMessage("FILE_NOT_EMPTY_ERROR","上传文件不可为空"));
        }

        // 生成文件Id
        String fileId = UUID.randomUUID().toString();

        // 获得文件名
        String fileOriginalName = file.getOriginalFilename();
        log.info("获取上传的文件名=[{}]", fileOriginalName);
        // 获取文件后缀
        String fileSuffix = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1);

        // 生成文件名称
        String newFileName = MessageFormat.format("{0}{1}.{2}",
                DateUtil.toString(DateUtil.getCurDate(),"yyyyMMddHHmmss"),
                fileId.substring(0,5),
                fileSuffix);
        log.info("生成新的文件名称=[{}]", newFileName);

        // 0配置根路径 + 1路径分割符 + 2日期年月日 + 3路径分割符
        String fullFolderPath = MessageFormat.format("{0}{1}{2}",
                fileConfig.getUploadPath(),
                File.separator,
                DateUtil.toString(DateUtil.getCurDate(),"yyyyMMdd"));
        log.info("上传文件的路径=[{}]", fullFolderPath);

        File fileUIS = new File(fullFolderPath);
        if(!fileUIS.exists()){// 如果不存在文件夹创建文件夹,每天第一次上传文件会创建yyyyMMdd的文件夹
            fileUIS.mkdirs();
        }

        String fileFullName = MessageFormat.format("{0}{1}{2}",
                fullFolderPath, File.separator, newFileName);

        File fullPathFile = new File(fileFullName);
        try {
            file.transferTo(fullPathFile); //保存文件
        } catch (IOException | IllegalStateException ex) {
            log.error("文件=[{}] 上传失败，异常信息=[{}]", fileOriginalName, ex);
            throw new BizLogicException(new SystemMessage("FILE_UPLOAD_ERROR","文件上传失败!"));
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(fileId);// 文件Id
        fileInfo.setFileName(newFileName); // 文件名
        fileInfo.setFileFullName(fileFullName);// 文件全路径
        fileInfo.setFileOriginalName(fileOriginalName);
        fileInfo.setFileSize(file.getSize());
        int insertResult = fileInfoDao.insert(fileInfo);
        log.info("文件上传数据记录成功=[{}]", insertResult);

        return newFileName;
    }

    /**
     * 文件预览
     * @param fileName
     * @return
     * @throws Exception
     */
    public File preview(String fileName) throws Exception {
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (Arrays.asList(StringUtils.split(fileConfig.getPreviewImgType().toLowerCase(),",")).contains(fileSuffix.toLowerCase())
            || Arrays.asList(StringUtils.split(fileConfig.getPreviewTextType().toLowerCase(),",")).contains(fileSuffix.toLowerCase())) {
            // 可根据文件后缀不同，执行特殊处理
            log.debug("该文件类型类型=[{}]支持预览", fileSuffix);
        } else {
            log.error("该文件类型类型=[{}]暂不支持预览", fileSuffix);
            throw new BizLogicException(new SystemMessage("FILE_NOT_PREVIEW","该文件类型类型暂不支持预览，请下载后查看！"));
        }

        String fullName = getFileInfo(fileName).getFileFullName();
        File file = new File(fullName);
        return file;
    }

    /**
     * 通过fileName 获取fileInfo信息
     * @return
     */
    public FileInfo getFileInfo(String fileName) throws Exception{
        FileInfo fileInfo = fileInfoDao.findByFileName(fileName);
        if (fileInfo == null || "".equals(fileInfo.getId())) {
            throw new BizLogicException(new SystemMessage("FILE_NOT_FOUNT","文件信息没有找到，无法预览！"));
        }
        return fileInfo;
    }
}
