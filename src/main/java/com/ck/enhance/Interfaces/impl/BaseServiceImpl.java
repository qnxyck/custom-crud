package com.ck.enhance.Interfaces.impl;


import com.ck.enhance.Interfaces.BaseMapper;
import com.ck.enhance.Interfaces.BaseService;
import com.ck.enhance.toolkit.ClassUtils;
import com.ck.enhance.toolkit.TableInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

import static com.ck.enhance.toolkit.MapUtils.setMap1;
import static com.ck.enhance.toolkit.MapUtils.setMap2;
import static com.ck.enhance.toolkit.TableInfoHelper.getTableInfoCache;


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

    public T getOne(Object id) {
        return baseMapper.selectById(setMap2(Objects.requireNonNull(id, "查询 ID 不能为 Null..."),
                null, getTableInfoCache(clazz)));
    }

    @Override
    public T getOne(Object id, Collection<String> tableColumns) {
        // 没有传入自定义列执行查询所有字段方法
        if (Objects.isNull(tableColumns)) {
            return getOne(id);
        }
        return baseMapper.selectById(setMap2(Objects.requireNonNull(id, "查询 ID 不能为 Null..."),
                tableColumns, getTableInfoCache(clazz)));

    }

    @Transactional
    @Override
    public boolean removeById(Object id) {
        return this.baseMapper.deleteById(setMap2(Objects.requireNonNull(id, "删除 ID 不能为Null..."),
                null, getTableInfoCache(clazz)));
    }

    @Transactional
    @Override
    public boolean save(T entity) {
        // 排除id, 使用数据库自增
        return this.baseMapper.insert(setMap1(Objects.requireNonNull(entity, "保存对象不能为Null..."),
                getTableInfoCache(clazz), true, false));
    }

    @Transactional
    @Override
    public boolean saveIdWorkerKey(T entity) {
        return this.baseMapper.insert(setMap1(Objects.requireNonNull(entity, "保存对象不能为Null..."),
                getTableInfoCache(clazz), false, false));
    }

    @Transactional
    @Override
    public boolean updateById(T entity) {
        return this.baseMapper.updateById(setMap1(Objects.requireNonNull(entity, "更新对象不能为Null..."),
                getTableInfoCache(clazz), false, true));
    }
}
