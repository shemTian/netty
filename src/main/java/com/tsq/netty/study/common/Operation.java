package com.tsq.netty.study.common;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 16:11
 */
public abstract class Operation extends MessageBody {
    public abstract OperationResult execute();
}
