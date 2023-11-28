package com.xiang.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source,Class<V> clazz) {
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <V> List<V> copyBeanList(List<?> source,Class<V> clazz) {
        return source.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
