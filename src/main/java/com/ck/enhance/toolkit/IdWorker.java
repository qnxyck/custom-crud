package com.ck.enhance.toolkit;


import com.ck.enhance.toolkit.support.Sequence;

import java.util.UUID;

/**
 * 生成唯一主键
 *
 * @author 陈坤
 * @serial 2018/12/25
 */
public class IdWorker {

    /**
     * 主机和进程的机器码
     */
    private static final Sequence worker = new Sequence();

    public static long getId() {
        return worker.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(worker.nextId());
    }

    /**
     * 获取去掉"-" UUID
     */
    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}