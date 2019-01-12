package com.ck.enhance.toolkit;


import com.ck.enhance.annotations.TableExcludeColumns;
import com.ck.enhance.annotations.TableName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 获取实体类对应信息
 * 缓存数据信息
 *
 * @author 陈坤
 * @serial 2019/1/8
 */
@Slf4j
public class TableInfoHelper {

    // 缓存映射关系
    private static final Map<Class<?>, TableInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 初始化表映射关系
     *
     * @param clazz .
     * @author 陈坤
     */
    public static synchronized void initTableInfo(Class<?> clazz) {
        TableInfo tableInfo = new TableInfo();
        // 判断是否存在 TableName 注解, 存在则解析注解, 否则使用类名作为查询表名称
        if (clazz.isAnnotationPresent(TableName.class)) {
            TableName annotation = clazz.getAnnotation(TableName.class);
            tableInfo.setTableName(annotation.value());
        } else {
            // 没有注解指定表名时, 使用当前类名称, 进行逆向驼峰转换
            String cName = clazz.getName();
            String conversionName = StringUtils.humpConversion(cName.substring(cName.lastIndexOf(".") + 1), true);
            tableInfo.setTableName(conversionName);
            log.warn("未指定实体类映射表注解! {}", clazz.getName());
        }

        // 初始化字段
        initTableColumn(tableInfo, clazz);
        // 将当前 实体类class也存入缓存
        tableInfo.setClazz(clazz);
        // 添加到缓存
        TABLE_INFO_CACHE.put(clazz, tableInfo);
        log.info("实体类映射关系处理完成: {}", clazz.getName());

    }


    /**
     * 初始化 TABLE_COLUMN
     *
     * @param tableInfo .
     * @author 陈坤
     */
    private static void initTableColumn(TableInfo tableInfo, Class<?> clazz) {
        // 需要排除的数据
        List<String> list = new ArrayList<>();
        List<String> tableColumnList = new ArrayList<>();

        // 存在注解将进行排除指定内容
        TableExcludeColumns excludeColumns = clazz.getAnnotation(TableExcludeColumns.class);
        if (!Objects.isNull(excludeColumns)) {
            list = Arrays.asList(excludeColumns.value());
        }
        // 取出当前实体类 所有字段
        for (Field field : clazz.getDeclaredFields()) {
            // 是否排除指定的字段
            if (!(!list.isEmpty() && list.contains(field.getName())))
                tableColumnList.add(field.getName());
        }

        // 如果有继承指定父类, 则初始化
        if (clazz.getSuperclass() == Model.class) {
            for (Field declaredField : clazz.getSuperclass().getDeclaredFields()) {
                // 是否排除指定的字段
                if (!(!list.isEmpty() && list.contains(declaredField.getName())))
                    tableColumnList.add(declaredField.getName());
            }
        }

        tableInfo.setTableColumnList(tableColumnList);
        tableInfo.setTableColumns(tableColumnList.stream()
                // 驼峰转换
                .map(s -> StringUtils.humpConversion(s, false))
                // 分割数据
                .collect(Collectors.joining(", ")));
    }

    /**
     * 在缓存中获取对应的数据
     *
     * @param clazz c
     * @return TableInfo
     * @author 陈坤
     */
    public static TableInfo getTableInfoCache(Class<?> clazz) {
        return TABLE_INFO_CACHE.get(clazz);
    }

    /**
     * 获取当前所有缓存信息
     *
     * @return Map
     * @author 陈坤
     */
    public static Map<Class<?>, TableInfo> getTableInfoCache() {
        return TABLE_INFO_CACHE;
    }


    public static String tableColumns(Collection<String> list) {
        return list.stream()
                // 驼峰转换
                .map(s -> StringUtils.humpConversion(s, false))
                // 分割数据
                .collect(Collectors.joining(", "));
    }

}