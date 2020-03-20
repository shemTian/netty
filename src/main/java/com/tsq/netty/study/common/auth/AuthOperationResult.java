package com.tsq.netty.study.common.auth;


import com.tsq.netty.study.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
