package com.tsq.netty.study.server.handler;

import com.tsq.netty.study.common.Operation;
import com.tsq.netty.study.common.RequestMessage;
import com.tsq.netty.study.common.auth.AuthOperation;
import com.tsq.netty.study.common.auth.AuthOperationResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 17:01
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        try {
            Operation operation = msg.getMessageBody();
            if (operation instanceof AuthOperation) {
                AuthOperation authOperation = (AuthOperation) operation;
                AuthOperationResult authOperationResult = authOperation.execute();
                if (authOperationResult.isPassAuth()) {
                    log.info("pass auth");
                }else {
                    log.error("fail to auth");
                    ctx.close();
                }
            }else {
                log.error("expect first msg is auth");
                ctx.close();
            }
        } catch (Exception e) {
            log.error("exception happen for :" + e.getMessage(), e);
            ctx.close();
        } finally {
            ctx.pipeline().remove(this);
        }
    }
}
