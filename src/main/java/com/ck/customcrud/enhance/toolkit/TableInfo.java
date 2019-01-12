package com.ck.customcrud.enhance.toolkit;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 陈坤
 * @serial 2019/1/9
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    // 表名称
    private String TableName;

    // 当前类的class
    private Class<?> clazz;

    // 当前类的所有字段
    private String tableColumns;

    private List<String> tableColumnList;

}
