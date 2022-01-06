package com.soul.rat.common.exception;


import com.soul.rat.common.api.BizCode;

/**
 * @author zhujx
 */
public class BizException extends AppException {

    private static final long serialVersionUID = 1L;

    public BizException() {
    }

    public BizException(BizCode errorInfo) {
        super(errorInfo);
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

}