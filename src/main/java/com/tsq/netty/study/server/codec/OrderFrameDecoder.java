package com.tsq.netty.study.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 16:29
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(10240, 0, 2, 0, 2);
    }
}
