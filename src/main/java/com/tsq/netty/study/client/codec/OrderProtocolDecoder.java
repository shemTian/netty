package com.tsq.netty.study.client.codec;

import com.tsq.netty.study.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 16:45
 */
@Slf4j
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.info("inbound OrderProtocolDecoder do...");
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(msg);
        out.add(responseMessage);
    }
}
