package com.lfw.purse.exception;

import com.lfw.purse.enums.ErrorCodeEnum;

/**
 * @author 龙发文
 * @ClassName BusinessException
 * @Description TODO
 * @date 2022/11/21 0021 22:01
 */
public class BusinessException extends RuntimeException {

    private int errorCode;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.msg());
        this.errorCode = errorCodeEnum.code();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.msg(),cause);
        this.errorCode = errorCodeEnum.code();
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = 500;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 500;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
