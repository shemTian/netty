package com.tsq.netty.study.common;

import com.tsq.netty.study.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * 这里添加类描述
 *
 * @author shemtian
 * @version 1.0.0
 * @Date 2020-03-20 15:42
 */
@Data
public abstract class Message<T extends MessageBody> {
    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }
    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        int opCode = msg.readInt();
        long streamId = msg.readLong();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        this.messageHeader = messageHeader;

        Class<T> messageBodyClass = getMessageBodyClass(opCode);

        T body = JsonUtil.fromJson(msg.toString(Charset.forName("UTF-8")), messageBodyClass);
        this.messageBody = body;
    }

    /**
     * 根据操作类型获取messageBody对象类型
     * @param opCode
     * @return
     */
    public abstract Class<T> getMessageBodyClass(int opCode);


}
