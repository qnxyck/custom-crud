package com.ck.enhance.toolkit;


import com.ck.enhance.enumerations.TableInfoEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 陈坤
 * @serial 2019/1/11
 */
@Slf4j
public class MapUtils {

    /**
     * bean对象转map
     *
     * @param obj         bean
     * @param excludeId   是否排除id
     *                    false   不排除id, 使用自定义主键
     *                    true    排除id, 使用数据库自增主键
     *                    -
     * @param excludeNull 是否排除null值
     *                    false 不排除
     *                    true 排除
     * @return .
     * @author 陈坤
     */
    private static Map<String, Object> beanToMap(Object obj, Class<?> clazz, boolean excludeId, boolean excludeNull, List<String> strings) {
        Map<String, Object> params = new HashMap<>();
        for (String name : strings) {
            // 逆向驼峰转换
            String s = StringUtils.humpConversion(name, false);
            Object property;
            try {
                Field declaredField = clazz.getDeclaredField(name);
                declaredField.setAccessible(true);
                property = declaredField.get(obj);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            // 是否排除null值
            if (excludeNull) {
                if (null != property)
                    params.put(s, property);
            } else {
                params.put(s, property);
            }
        }
        // 排除id
        if (excludeId)
            params.remove(TableInfoEnum.id.name());
        return params;
    }

    /**
     * 构建Map信息
     * @param obj          操作实体
     * @param excludeId    .
     * @param excludeNull  .
     * @return Map
     * @author 陈坤
     */

    // 更新/修改
    public static Map<String, Object> setMap1(Object obj, TableInfo tableInfo, boolean excludeId, boolean excludeNull) {
        Map<String, Object> map = new HashMap<>();
        Object id = null;
        Map<String, Object> entityMap = beanToMap(obj, tableInfo.getClazz(), excludeId, excludeNull, tableInfo.getTableColumnList());
        /*
         * 不排除id并且id为null, 则使用IdWorker生成主键.
         */
        if (!excludeId) {
            if (entityMap.get(TableInfoEnum.id.name()) == null) {
                id = IdWorker.getId();
            } else {
                id = entityMap.get(TableInfoEnum.id.name());
            }
            entityMap.remove(TableInfoEnum.id.name());
        }
        // 设置实体类信息
        map.put(TableInfoEnum.ENTITY_MAP.name(), entityMap);
        // 初始化基本信息
        map.putAll(setMap2(id, entityMap.keySet(), tableInfo));
        return map;
    }

    // 查询/删除
    public static Map<String, Object> setMap2(Object id, Collection<String> tableColumns, TableInfo tableInfo) {
        Map<String, Object> map = new HashMap<>();
        // 缓存中获取表名称
        map.put(TableInfoEnum.TABLE_NAME.name(), tableInfo.getTableName());
        // 添加id
        if (!Objects.isNull(id)) {
            map.put(TableInfoEnum.TABLE_ID_VALUE.name(), id);
        }
        // 判断自定义列还是默认, 为 null 使用默认
        if (tableColumns != null) {
            // 添加列
            map.put(TableInfoEnum.TABLE_COLUMN.name(), TableInfoHelper.tableColumns(tableColumns));
        } else {
            map.put(TableInfoEnum.TABLE_COLUMN.name(), tableInfo.getTableColumns());
        }
        return map;
    }
}
