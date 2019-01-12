package com.ck.customcrud.enhance.toolkit;


import com.ck.customcrud.enhance.enumerations.TableInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;

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
    private static Map<String, Object> beanToMap(Object obj, boolean excludeId, boolean excludeNull, List<String> strings) {
        Map<String, Object> params = new HashMap<>();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        for (String name : strings) {
            // 逆向驼峰转换
            String s = StringUtils.humpConversion(name, false);
            Object property;
            try {
                property = propertyUtilsBean.getNestedProperty(obj, name);
            } catch (Exception e) {
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
     *
     * @param id           id
     * @param tableColumns 自定义字段
     * @param clazz        c
     * @param obj          操作实体
     * @param excludeId    .
     * @param excludeNull  .
     * @return Map
     * @author 陈坤
     */
    public static Map<String, Object> setMap(Object id, Collection<String> tableColumns, Class clazz, Object obj, boolean excludeId, boolean excludeNull) {
        Map<String, Object> map = new HashMap<>();
        TableInfo tableInfo = TableInfoHelper.getTableInfoCache(clazz);
        // 添加/修改参数中的 表名称
        map.put(TableInfoEnum.TABLE_NAME.name(), tableInfo.getTableName());
        // 添加id
        if (!Objects.isNull(id)) {
            map.put(TableInfoEnum.TABLE_ID_VALUE.name(), id);
        }
        // 判断自定义列还是默认, 为 null 使用默认
        if (tableColumns != null) {
            // 添加列
            map.put(TableInfoEnum.TABLE_COLUMN.name(), String.join(",", tableColumns));
        } else {
            map.put(TableInfoEnum.TABLE_COLUMN.name(), tableInfo.getTableColumns());
        }
        // 查询/删除 obj为Null
        if (!Objects.isNull(obj)) {
            Map<String, Object> entityMap = beanToMap(obj, excludeId, excludeNull, tableInfo.getTableColumnList());
            /*
             * 不排除id并且id为null, 则使用IdWorker生成主键.
             */
            if (!excludeId && entityMap.get(TableInfoEnum.id.name()) == null)
                entityMap.put(TableInfoEnum.id.name(), IdWorker.getId());
            // 设置实体类信息
            map.put(TableInfoEnum.ENTITY_MAP.name(), entityMap);
        }
        return map;
    }

}
