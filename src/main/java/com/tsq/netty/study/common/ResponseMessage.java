package com.tsq.netty.study.common;

public class ResponseMessage extends Message <OperationResult>{
    @Override
    public Class getMessageBodyClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationResultClazz();
    }
}
