package com.lfw.purse.enums;


public enum StateCodeEnum {
    SUCCESS(200, "请求成功"),

    CONSTRAINT_VIOLATION(502, "方法参数校验出错"),

    METHOD_ARGUMENT_NOT_VALID(501, "方法参数无效"),

    ERROR(500, "服务器异常"),

    /**
     * 其他自定义业务异常
     */
    NO_LOGIN (100, "未登录"),

    ADD_EXCEPTION(5001,"新增异常"),
    DELETE_EXCEPTION(5002,"删除异常"),
    UPDATE_EXCEPTION(5003,"更新异常"),
    BIND_TELEPHONE(5004,"手机号已经被绑定");

    private String msg;
    private int code;

    public String msg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    StateCodeEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

}
