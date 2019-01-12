package com.ck.enhance.toolkit;

import java.lang.reflect.ParameterizedType;

/**
 * @author 陈坤
 * @serial 2019/1/9
 */
public class ClassUtils {

    /**
     * 获取父类泛型class
     *
     * @param _this    操作对象
     * @param position 取第几个泛型
     * @param <T>      .
     * @return Class
     * @author 陈坤
     */
    public static <T> Class<T> entityClass(Object _this, int position) {
        ParameterizedType type = (ParameterizedType) _this.getClass().getGenericSuperclass();
        //noinspection unchecked
        return (Class<T>) type.getActualTypeArguments()[position];
    }
}
