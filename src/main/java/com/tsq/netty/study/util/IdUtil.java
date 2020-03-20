package com.tsq.netty.study.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 16:03
 */
public class IdUtil {
    private static AtomicLong atomicLong = new AtomicLong();

    private IdUtil() {
        // no instance
    }

    public static long nextId() {
        return atomicLong.incrementAndGet();
    }
}
