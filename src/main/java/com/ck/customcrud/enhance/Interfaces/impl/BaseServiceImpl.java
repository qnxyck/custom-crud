package com.ck.customcrud.enhance.Interfaces.impl;


import com.ck.customcrud.enhance.Interfaces.BaseMapper;
import com.ck.customcrud.enhance.Interfaces.BaseService;
import com.ck.customcrud.enhance.toolkit.ClassUtils;
import com.ck.customcrud.enhance.toolkit.TableInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

import static com.ck.customcrud.enhance.toolkit.MapUtils.setMap;


/**
 * @author 陈坤
 * @serial 2019/1/8
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    // 泛型实体类实际class
    private final Class<T> clazz;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private M baseMapper;

    public BaseServiceImpl() {
        // 获取实体类泛型class
        clazz = ClassUtils.entityClass(this, 1);
        // 简单初始化映射关系
        TableInfoHelper.initTableInfo(clazz);
    }

    /**
     * 根据id查询
     * 默认查询所有字段
     * 如果查询指定字段, 请使用重载方法
     *
     * @param id id
     * @return T
     * @author 陈坤
     */
    public T getOne(Object id) {
        return baseMapper.selectById(setMap(Objects.requireNonNull(id, "查询 ID 不能为 Null..."),
                null, clazz, null, false, false));
    }

    /**
     * 根据id查询
     *
     * @param id           id
     * @param tableColumns 自定义查询列
     * @return T
     * @author 陈坤
     */
    @Override
    public T getOne(Object id, Collection<String> tableColumns) {
        // 没有传入自定义列执行查询所有字段方法
        if (Objects.isNull(tableColumns)) {
            return getOne(id);
        }
        return baseMapper.selectById(setMap(Objects.requireNonNull(id, "查询 ID 不能为 Null..."), tableColumns,
                clazz, null, false, false));
    }

    @Transactional
    @Override
    public boolean removeById(Object id) {
        return this.baseMapper.deleteById(setMap(Objects.requireNonNull(id, "删除 ID 不能为 Null..."),
                null, clazz, null, false, false));
    }

    @Transactional
    @Override
    public boolean save(T entity) {
        // 排除id, 使用数据库自增
        return this.baseMapper.insert(setMap(null, null, clazz,
                Objects.requireNonNull(entity, "保存 对象不能为 Null..."), true, false));
    }

    @Transactional
    @Override
    public boolean saveIdWorkerKey(T entity) {
        // 不排除id, 使用自定义主键
        return this.baseMapper.insert(setMap(null, null, clazz, entity, false, false));
    }

    @Transactional
    @Override
    public boolean updateById(T entity) {
        if (Objects.isNull(entity))
            throw new NullPointerException("更新对象不能为 Null...");
        // 排除id 排除空值
        return this.baseMapper.updateById(setMap(null, null, clazz, entity, true, true));
    }
}
