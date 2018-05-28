package com.grepiu.www.process.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * 필터 Utilt이다.
 *
 * @author JM
 * @Since 2017.10.24/v1.0
 */
public class FilterUtil {

    /**
     *
     * 필터를 통해 값을 가져온다.
     *
     * @param list
     * @param p
     * @param <T>
     * @return
     */
    public static <T> List<T> listFilter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T obj : list) {
            if(p.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }
}
