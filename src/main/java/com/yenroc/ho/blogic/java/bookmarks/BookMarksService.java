package com.yenroc.ho.blogic.java.bookmarks;

import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksReqt;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksResp;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksRespS01;
import com.yenroc.ho.blogic.restDto.bookmarks.BookMarksRespS02;
import com.yenroc.ho.blogic.sqlDto.bookmarks.BookMarksSQL01OM;
import com.yenroc.ho.common.dao.QueryDao;
import com.yenroc.ho.utils.BeanCopierEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookMarksService {

    @Autowired
    private QueryDao queryDao;

    public BookMarksResp bookMarksQuery(BookMarksReqt params){
        BookMarksResp result = new BookMarksResp();

        List<BookMarksSQL01OM> sql01OMList = queryDao.executeForObjectList("BookMarksQuery01", params);
        if (sql01OMList.isEmpty()) {
            return result;
        }
        List<BookMarksRespS01> bookMarksFolder = new ArrayList();
        for (BookMarksSQL01OM sql01OM : sql01OMList) {
            boolean addFlag = false;
            BookMarksRespS02 bookMark = new BookMarksRespS02();
            for (BookMarksRespS01 folder : bookMarksFolder) {
                if (folder.getFolderName().equals(sql01OM.getFolderName())) {
                    BeanCopierEx.copy(sql01OM, bookMark);
                    folder.getBookMarks().add(bookMark);
                    addFlag = true;
                }
            }
            if (!addFlag) {
                BookMarksRespS01 s01 = new BookMarksRespS01();
                s01.setFolderName(sql01OM.getFolderName());
                s01.setFolderSvg(sql01OM.getFolderSvg());
                BeanCopierEx.copy(sql01OM, bookMark);
                s01.getBookMarks().add(bookMark);
                bookMarksFolder.add(s01);
            }
        }
        result.setBookMarksFolder(bookMarksFolder);
        return  result;
    }
}
