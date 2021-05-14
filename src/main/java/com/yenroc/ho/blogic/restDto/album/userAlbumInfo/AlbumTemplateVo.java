package com.yenroc.ho.blogic.restDto.album.userAlbumInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 相册模板
 */
@Data
public class AlbumTemplateVo implements Serializable {

    private static final long serialVersionUID = -3425847102168704004L;

    // 模板Id
    private Integer templateId;

    // 模板名称
    private String templateName;

    // 模板描述
    private String templateDesc;

    // 模板预览的实例Id
    private Integer defaultInstanceId;

    private String templateStyleCss;

    private String viewPhotoUrl;

}
