package com.yenroc.ho.blogic.java.photoUpdate;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.user.UserCreateAndLoginBLogic;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumReqt;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumReqtM01;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumResp;
import com.yenroc.ho.blogic.restDto.album.photoUpdate.PhotoUpdateReqt;
import com.yenroc.ho.blogic.restDto.album.photoUpdate.PhotoUpdateResp;
import com.yenroc.ho.common.service.BizLogic;
import com.yenroc.ho.config.BlogGlobalConfig;
import com.yenroc.ho.mapper.AlbumInstanceDao;
import com.yenroc.ho.mapper.AlbumPhotoInstanceDao;
import com.yenroc.ho.mapper.AlbumTemplateDao;
import com.yenroc.ho.mapper.FileInfoDao;
import com.yenroc.ho.mapper.entity.AlbumInstance;
import com.yenroc.ho.mapper.entity.AlbumPhotoInstance;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import com.yenroc.ho.mapper.entity.FileInfo;
import com.yenroc.ho.utils.BeanCopierEx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PhotoUpdateBLogic")
@Transactional(rollbackFor = Exception.class)
public class PhotoUpdateBLogic implements BizLogic<PhotoUpdateReqt, PhotoUpdateResp> {

    private static final Logger log = LoggerFactory.getLogger(UserCreateAndLoginBLogic.class);

    @Autowired
    private BlogGlobalConfig blogGlobalConfig;

    @Autowired
    private AlbumPhotoInstanceDao albumPhotoInstanceDao;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public PhotoUpdateResp execute(PhotoUpdateReqt arg0) throws Exception {
        PhotoUpdateResp result = new PhotoUpdateResp();

        // 根据文件,获取文件的id
        FileInfo fileInfo = fileInfoDao.findByFileName(arg0.getFileName());
        if (fileInfo != null) {
            String photoUrl = blogGlobalConfig.getPhotoViewUrl() + fileInfo.getFileFullPath();
            result.setPhotoUrl(StringUtils.replace(photoUrl,"\\","/"));
            result.setFileId(fileInfo.getId());
        }
        Integer id = arg0.getId();
        if (null != id) {
            AlbumPhotoInstance albumPhotoInstance = albumPhotoInstanceDao.selectByPrimaryKey(id);
            if (fileInfo != null) {
                albumPhotoInstance.setFileId(fileInfo.getId());
            }
            albumPhotoInstanceDao.updateByPrimaryKey(albumPhotoInstance);
        }
        return result;
    }
}
