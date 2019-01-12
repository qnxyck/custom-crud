package com.ck.customcrud.enhance.Interfaces;

import java.util.Collection;

/**
 * @author 陈坤
 * @serial 2019/1/8
 */
public interface BaseService<T> {

    /**
     * 根据id查询单个数据
     * 默认查询所有字段
     *
     * @param id id
     * @return T
     * @author 陈坤
     */
    T getOne(Object id);


    /**
     * 根据id查询单个数据
     * 可指定查询出所需字段
     *
     * @param id          id
     * @param tableColumn column
     * @return T
     * @author 陈坤
     */
    T getOne(Object id, Collection<String> tableColumn);


    /**
     * 根据id删除一条数据
     *
     * @param id id
     * @return boolean
     * @author 陈坤
     */
    boolean removeById(Object id);


    /**
     * 插入一条数据
     * <p>
     * 默认使用数据库主键自增
     * 即使你指定了id值, 也无法使用
     * 如果要使用自定义id, 请使用 boolean saveIdWorkerKey(T entity); 方法
     *
     * @param entity e
     * @return boolean
     * @author 陈坤
     */
    boolean save(T entity);

    /**
     * 如果实体类id为null, 则使用 IdWorker 成id
     *
     * @param entity entity
     * @return boolean
     * @author 陈坤
     */
    boolean saveIdWorkerKey(T entity);


    /**
     * 根据id更新实体类所有不为null的字段
     *
     * @param entity entity
     * @return boolean
     * @author 陈坤
     */
    boolean updateById(T entity);
}
