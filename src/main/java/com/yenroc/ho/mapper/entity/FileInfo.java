package com.yenroc.ho.mapper.entity;

import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="file")
public class FileInfo extends SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -7241063697538687240L;

    // 文件id
    @Column(name = "id")
    private String id;

    // 文件名称：作为文件访问路径
    @Column(name = "fileName")
    private String fileName;

    // 文件上传路径
    @Column(name = "fileFullName")
    private String fileFullName;

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

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
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

}
