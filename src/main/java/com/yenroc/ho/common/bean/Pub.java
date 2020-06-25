package com.yenroc.ho.common.bean;

import java.util.List;

import com.github.pagehelper.Page;
import com.yenroc.ho.common.consts.CommonConsts;
import com.yenroc.ho.common.context.BLogContext;

public class Pub {

    /**
     * 通过查询结果把信息放到上下文中
     * @param list
     * @param <E>
     * @return
     * @throws Exception
     */
    public static <E> PageInfo setPageInfo(List<E> list) throws Exception {
        if (list == null) {
            return null;
        }
        if (!(list instanceof Page<?>)) {
            return null;
        }
        Page<E> page = (Page<E>) list;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        pageInfo.setTotalRecordCount(page.getTotal());
        pageInfo.setTotalPages(page.getPages());
        BLogContext.setValue(CommonConsts.PAGE_INFO, pageInfo);
        return pageInfo;
    }

}
