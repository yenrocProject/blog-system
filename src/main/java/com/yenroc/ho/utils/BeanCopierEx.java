package com.yenroc.ho.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.core.ReflectUtils;

public class BeanCopierEx {

    public static <S, T> void copy(S s, T t) {
        BeanCopier copier = BeanCopier.create(s.getClass(), t.getClass(), false);
        copier.copy(s, t, (Converter) null);
    }

    public static <S, T> ArrayList<T> copy(List<S> sList, Class<T> clazz)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (sList != null && sList.size() != 0) {
            BeanCopier copier = BeanCopier.create(sList.get(0).getClass(), clazz, false);
            ArrayList tList = new ArrayList();
            Iterator arg3 = sList.iterator();

            while (arg3.hasNext()) {
                Object s = arg3.next();
                Object t = ReflectUtils.newInstance(clazz);
                copier.copy(s, t, (Converter) null);
                tList.add(t);
            }

            return tList;
        } else {
            return new ArrayList();
        }
    }
}
