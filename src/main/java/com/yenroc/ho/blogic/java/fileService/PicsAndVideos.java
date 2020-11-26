package com.yenroc.ho.blogic.java.fileService;

import com.yenroc.ho.vo.CommonUploadFileVO;

import java.io.InputStream;

/**
 * @desc: 图片视频缩略图处理
 */
public interface PicsAndVideos {

    /**
     * 描述:根据实际的图片地址生成缩略图，并返回缩略图的地址
     * @param commonUploadFileVO 通用上传的vo
     * @return String 缩略图的虚拟路径
     */
    public InputStream getThumbnailStream(CommonUploadFileVO commonUploadFileVO) throws Exception;

    /**
     * 描述:根据视频生成第多少秒的截图，并返回截图的实际地址和虚拟地址
     * @param commonUploadFileVO 通用上传的vo
     * @param second 需要截取第几秒的视频截图
     * @return String 视频截图的虚拟路径
     */
    public InputStream getVideoPicStream(CommonUploadFileVO commonUploadFileVO, int second) throws Exception;
}
