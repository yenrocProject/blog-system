package com.yenroc.ho.blogic.java.createOrUpdateAlbum;

import com.yenroc.ho.blogic.consts.AlbumConsts;
import com.yenroc.ho.blogic.java.user.UserCreateAndLoginBLogic;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumReqt;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumReqtM01;
import com.yenroc.ho.blogic.restDto.album.createOrUpdate.CreateOrUpdateAlbumResp;
import com.yenroc.ho.common.service.BizLogic;
import com.yenroc.ho.mapper.AlbumInstanceDao;
import com.yenroc.ho.mapper.AlbumPhotoInstanceDao;
import com.yenroc.ho.mapper.AlbumTemplateDao;
import com.yenroc.ho.mapper.FileInfoDao;
import com.yenroc.ho.mapper.entity.AlbumInstance;
import com.yenroc.ho.mapper.entity.AlbumPhotoInstance;
import com.yenroc.ho.mapper.entity.AlbumTemplate;
import com.yenroc.ho.utils.BeanCopierEx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CreateOrUpdateAlbumBLogic")
@Transactional(rollbackFor = Exception.class)
public class CreateOrUpdateAlbumBLogic implements BizLogic<CreateOrUpdateAlbumReqt, CreateOrUpdateAlbumResp> {

    private static final Logger log = LoggerFactory.getLogger(UserCreateAndLoginBLogic.class);

    @Autowired
    private AlbumTemplateDao albumTemplateDao;

    @Autowired
    private AlbumInstanceDao albumInstanceDao;

    @Autowired
    private AlbumPhotoInstanceDao albumPhotoInstanceDao;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Override
    public CreateOrUpdateAlbumResp execute(CreateOrUpdateAlbumReqt arg0) throws Exception {
        CreateOrUpdateAlbumResp result = new CreateOrUpdateAlbumResp();
        // 新增
        AlbumInstance albumInstance = new AlbumInstance();
        BeanCopierEx.copy(arg0, albumInstance);
        if (AlbumConsts.ALBUM_VIEW_PRIVATE.equals(albumInstance.getPrivateView())) {
            albumInstance.setPrivateKey(Thread.currentThread().getName());
        }
        if (arg0.getAlbumId() != null) {
            albumInstance.setId(arg0.getAlbumId());
            log.info("更新相册的描述信息...");
            albumInstanceDao.updateByPrimaryKeySelective(albumInstance);
        } else {
            // 设置相册默认照片
            AlbumTemplate albumTemplate = albumTemplateDao.selectByPrimaryKey(arg0.getAlbumTemplateId());
            AlbumInstance defaultAlbumInstance1 = albumInstanceDao.selectByPrimaryKey(albumTemplate.getDefaultInstanceId());
            albumInstance.setDefaultViewPhoto(defaultAlbumInstance1.getDefaultViewPhoto());

            log.info("进行相册的创建...");
            int insert = albumInstanceDao.insert(albumInstance);
            log.info("插入数据{},结果={}", albumInstance, insert);

            Integer albumId = albumInstance.getId();

            // 创建相册照片
            String defaultViewPhotoUrl = "";
            List<CreateOrUpdateAlbumReqtM01> albumPhotos = arg0.getAlbumPhotos();
            if (albumPhotos.size() > 0) {
                // 循环照片相册,存在 照片Id 为空, 则新增.照片Id 存在,则更新
                for (CreateOrUpdateAlbumReqtM01 albumPhoto : albumPhotos) {
                    AlbumPhotoInstance albumPhotoInstance = new AlbumPhotoInstance();
                    albumPhotoInstance.setAlbumInstanceId(albumId);
                    // 表示新上传的文件
                    if (albumPhoto.getId() == null) {
                        albumPhotoInstance.setFileId(fileInfoDao.findByFileName(albumPhoto.getFileName()).getId());
                    }
                    // 取第一个照片作为该相册的默认显示照片
                    if (StringUtils.isBlank(defaultViewPhotoUrl) && albumPhotoInstance.getFileId() != null){
                        defaultViewPhotoUrl = fileInfoDao.selectByPrimaryKey(albumPhotoInstance.getFileId()).getFileFullPath();
                    }
                    albumPhotoInstance.setTemplateId(arg0.getAlbumTemplateId());
                    albumPhotoInstance.setUserId(arg0.getUserId());
                    if (albumPhoto.getId() == null) {
                        albumPhotoInstanceDao.insert(albumPhotoInstance);
                    }
                }
            }
            // 不为空,则更新进去
            if (StringUtils.isNotBlank(defaultViewPhotoUrl)) {
                albumInstance.setDefaultViewPhoto(defaultViewPhotoUrl);
                albumInstanceDao.updateByPrimaryKey(albumInstance);
            }
        }

        result.setAlbumId(albumInstance.getId());
        return result;
    }
}
