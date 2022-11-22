package com.lfw.purse.dto;

import com.lfw.purse.enums.StateCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 龙发文
 * @ClassName ResultWrapper
 * @Description TODO
 * @date 2022/11/21 0021 21:59
 */
public class ResultWrapper<T> implements Serializable {

    /**
     * 响应提示信息
     */
    private String msg;
    /**
     * 返回码：200正常，500以上为错误信息
     */
    private int code;
    /**
     * 返回
     */
    private T data;

    public static <T> ResultWrapper ok(T data){
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(StateCodeEnum.SUCCESS.getCode());
        resultWrapper.setMsg(StateCodeEnum.SUCCESS.msg());
        resultWrapper.setData(data);
        return  resultWrapper;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
