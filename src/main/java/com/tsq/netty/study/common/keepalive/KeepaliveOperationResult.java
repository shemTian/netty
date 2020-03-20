package com.tsq.netty.study.common.keepalive;

import com.tsq.netty.study.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
