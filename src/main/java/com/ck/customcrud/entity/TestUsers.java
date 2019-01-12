package com.ck.customcrud.entity;

import com.ck.enhance.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 陈坤
 * @serial 2019/1/12
 */
@TableName("users")
@Data
@Accessors(chain = true)
public class TestUsers {
    private Long id;
    private String userName;
    private Integer age;

}
