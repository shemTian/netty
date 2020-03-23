package com.tsq.netty.study.client;

import com.tsq.netty.study.client.handler.dispatcher.OperationResultFuture;
import com.tsq.netty.study.client.handler.dispatcher.RequestPendingCenter;
import com.tsq.netty.study.client.handler.dispatcher.ResponseDispatcherHandler;
import com.tsq.netty.study.common.OperationResult;
import com.tsq.netty.study.common.RequestMessage;
import com.tsq.netty.study.common.order.OrderOperation;
import com.tsq.netty.study.client.codec.OrderFrameDecoder;
import com.tsq.netty.study.client.codec.OrderFrameEncoder;
import com.tsq.netty.study.client.codec.OrderProtocolDecoder;
import com.tsq.netty.study.client.codec.OrderProtocolEncoder;
import com.tsq.netty.study.util.IdUtil;
import com.tsq.netty.study.util.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-21 11:31
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws CertificateException, SSLException {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.channel(NioSocketChannel.class);

        //thread
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));


        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        try {
            bootstrap.group(workGroup);

            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    // 发送(ChannelOutboundHandler) 执行顺序和注册顺序相反 OrderProtocolEncoder -> OrderFrameEncoder
                    pipeline.addLast("frameEncoder", new OrderFrameEncoder());
                    pipeline.addLast("protocolEncoder", new OrderProtocolEncoder());


                    // 接收(ChannelInboundHandler) 执行顺序和注册顺序相同 OrderFrameDecoder->OrderProtocolDecoder->ResponseDispatcherHandler
                    pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                    pipeline.addLast("protocolDecoder", new OrderProtocolDecoder());
                    pipeline.addLast("responseDispatcher", new ResponseDispatcherHandler(requestPendingCenter));


                    pipeline.addLast("infoLog", new LoggingHandler(LogLevel.INFO));
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);
            channelFuture.sync();

            long streamId = IdUtil.nextId();
            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId, operationResultFuture);

            RequestMessage requestMessage = new RequestMessage(streamId,new OrderOperation(1,"1"));
            log.info("start send msg do...");
            channelFuture.channel().writeAndFlush(requestMessage);

            OperationResult operationResult = operationResultFuture.get();
            log.info(JsonUtil.toJson(operationResult));

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
