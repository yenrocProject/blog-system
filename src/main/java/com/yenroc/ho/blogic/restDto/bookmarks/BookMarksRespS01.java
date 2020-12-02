package com.yenroc.ho.blogic.restDto.bookmarks;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookMarksRespS01 {

    private String folderName;

    private String folderSvg;

    private List<BookMarksRespS02> bookMarks = new ArrayList<>();

}
