package com.yenroc.ho.blogic.restDto.bookmarks;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookMarksResp {

    private List<BookMarksRespS01> bookMarksFolder = new ArrayList<>();

}
