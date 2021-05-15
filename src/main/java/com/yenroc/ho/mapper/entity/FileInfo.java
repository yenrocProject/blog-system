package com.yenroc.ho.mapper.entity;

import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="file")
public class FileInfo extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    // 文件id
    @Id
    @Column(name = "id")
    private String id;

    // 文件名称：作为文件访问路径
    @Column(name = "fileName")
    private String fileName;

    // 文件上传路径
    @Column(name = "fileFullPath")
    private String fileFullPath;

    // 文件缩略图地址
    @Column(name = "thumbFullPath")
    private String thumbFullPath;

    // 文件原始名称
    @Column(name = "fileOriginalName")
    private String fileOriginalName;

    // 文件大小
    @Column(name = "fileSize")
    private Long fileSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbFullPath() {
        return thumbFullPath;
    }

    public void setThumbFullPath(String thumbFullPath) {
        this.thumbFullPath = thumbFullPath;
    }
}
