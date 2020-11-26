package com.yenroc.ho.blogic.java.fileService.impl;

import com.yenroc.ho.blogic.java.fileService.PicsAndVideos;
import com.yenroc.ho.blogic.java.fileService.constant.CommonFileConstant;
import com.yenroc.ho.utils.FileUtil;
import com.yenroc.ho.vo.CommonUploadFileVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PicsAndVideosImpl implements PicsAndVideos {

    private final static Logger log = LoggerFactory.getLogger(PicsAndVideosImpl.class);

    @Override
    public InputStream getThumbnailStream(CommonUploadFileVO uploadFileVO) throws Exception {
        if (uploadFileVO == null
                || uploadFileVO.getInputStream() == null
                || StringUtils.isBlank(uploadFileVO.getFileType())) {
            return null;
        }
        if (CommonFileConstant.JPG.equals(uploadFileVO.getFileType().toUpperCase())
                || CommonFileConstant.JPEG.equals(uploadFileVO.getFileType().toUpperCase())
                || CommonFileConstant.BMP.equals(uploadFileVO.getFileType().toUpperCase())
                || CommonFileConstant.PNG.equals(uploadFileVO.getFileType().toUpperCase())
                || CommonFileConstant.GIF.equals(uploadFileVO.getFileType().toUpperCase())) {
            ByteArrayOutputStream os = null;
            InputStream copyStream = null;
            try {
                // 复制一份流进行缩略操作
                copyStream = new ByteArrayInputStream(FileUtil.getCopyInputStream(uploadFileVO).toByteArray());
                // 定义要返回的数据流
                os = new ByteArrayOutputStream();
                Image src = ImageIO.read(copyStream);
                int srcHeight = src.getHeight(null);
                int srcWidth = src.getWidth(null);
                int deskHeight = 0;// 缩略图高
                int deskWidth = 0;// 缩略图宽
                double srcScale = (double) srcHeight / srcWidth;
                double comBase = CommonFileConstant.THUMBNAIL_COMBASE;//1920可以在不损失清晰度的情况下尽量减少体积
                double scale = 1.0d; //
                //缩略图宽高算法
                if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
                    if (srcScale >= scale || 1 / srcScale > scale) {
                        if (srcScale >= scale) {
                            deskHeight = (int) comBase;
                            deskWidth = srcWidth * deskHeight / srcHeight;
                        } else {
                            deskWidth = (int) comBase;
                            deskHeight = srcHeight * deskWidth / srcWidth;
                        }
                    } else {
                        if ((double) srcHeight > comBase) {
                            deskHeight = (int) comBase;
                            deskWidth = srcWidth * deskHeight / srcHeight;
                        } else {
                            deskWidth = (int) comBase;
                            deskHeight = srcHeight * deskWidth / srcWidth;
                        }
                    }
                } else {
                    deskHeight = srcHeight;
                    deskWidth = srcWidth;
                }

                BufferedImage bufferedImage = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
                // 绘制缩小后的图
                bufferedImage.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
                ImageIO.write(bufferedImage, uploadFileVO.getSuffixName(), os);
                InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
                return inputStream;
            } catch (Exception ex) {
                log.error("图片压缩出现错误：[文件名={}],错误信息：", uploadFileVO.getFileOriginalName(), ex);
                return null;
            } finally {
                if (copyStream != null) {
                    copyStream.close();
                }
                if (os != null) {
                    os.close();
                }
            }

        }

        return null;
    }

    @Override
    public InputStream getVideoPicStream(CommonUploadFileVO commonUploadFileVO, int second) throws Exception {
        return null;
    }
}
