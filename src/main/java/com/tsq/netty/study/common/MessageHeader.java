package com.tsq.netty.study.common;

import lombok.Data;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 15:41
 */
@Data
public class MessageHeader {
    private int version = 1;
    private int opCode;
    private long streamId;
}
