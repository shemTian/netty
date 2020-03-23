package com.tsq.netty.study.server.handler;

import com.tsq.netty.study.common.Operation;
import com.tsq.netty.study.common.OperationResult;
import com.tsq.netty.study.common.RequestMessage;
import com.tsq.netty.study.common.ResponseMessage;
import com.tsq.netty.study.util.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 17:20
 */
@Slf4j
public class OrderServeProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        Operation operation = msg.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(msg.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            ctx.writeAndFlush(responseMessage);
        }else {
            log.error("not writable now,message dropped:" + JsonUtil.toJson(responseMessage));
        }
    }
}
