package com.lfw.purse.advice;

import com.lfw.purse.JsonUtils;
import com.lfw.purse.dto.ResultWrapper;
import com.lfw.purse.enums.StateCodeEnum;
import com.lfw.purse.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龙发文
 * @ClassName BaseExceptionAdvice
 * @Description TODO
 * @date 2022/11/21 0021 21:58
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionAdvice implements ResponseBodyAdvice<Object> {

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public ResultWrapper bizException(BusinessException e) {
        log.error("出现异常:{}", e);
        ResultWrapper<Object> objectResultWrapper = new ResultWrapper<>();
        objectResultWrapper.setCode(e.getErrorCode());
        objectResultWrapper.setMsg(e.getMessage());
        return objectResultWrapper;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultWrapper methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("出现异常:{}", e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        // 已经设置了快速失败，可以默认取首个错误即可
        StringBuffer sb = new StringBuffer();
        List<String> errorArr = new ArrayList<>();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorArr.add(fieldError.getField() + fieldError.getDefaultMessage());
            } else {
                errorArr.add(error.getObjectName() + error.getDefaultMessage());
            }
        }
        String errMsg = String.join(";", errorArr.toArray(new String[]{}));

        ResultWrapper<Object> objectResultWrapper = new ResultWrapper<>();
        objectResultWrapper.setCode(StateCodeEnum.METHOD_ARGUMENT_NOT_VALID.getCode());
        objectResultWrapper.setMsg(errMsg);

        return objectResultWrapper;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultWrapper constraintViolationException(ConstraintViolationException e) {
        // 拦截单个参数校验异常捕获
        log.error("出现异常:{}", e);
        // @RequestParam 参数校验失败
        StringBuffer sb = new StringBuffer();
        List<String> errorArr = new ArrayList<>();
        for (ConstraintViolation constraint : e.getConstraintViolations()) {
            errorArr.add(constraint.getInvalidValue() + "非法" + constraint.getMessage());
        }
        String errMsg = String.join(";", errorArr.toArray(new String[]{}));

        ResultWrapper<Object> objectResultWrapper = new ResultWrapper<>();
        objectResultWrapper.setCode(StateCodeEnum.CONSTRAINT_VIOLATION.getCode());
        objectResultWrapper.setMsg(errMsg);

        return objectResultWrapper;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultWrapper exception(Exception e) {
        log.error("出现异常:{}", e);
        ResultWrapper<Object> objectResultWrapper = new ResultWrapper<>();
        objectResultWrapper.setCode(StateCodeEnum.ERROR.getCode());
        objectResultWrapper.setMsg(e.getMessage());
        return objectResultWrapper;
    }


    /**
     * 参数错误类型转换
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public String parameterTypeConvertException(HttpMessageConversionException e) {

        ResultWrapper<Object> objectResultWrapper = new ResultWrapper<>();
        objectResultWrapper.setCode(StateCodeEnum.ERROR.getCode());
        objectResultWrapper.setMsg(e.getMessage());

        return JsonUtils.toJsonString(objectResultWrapper);
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 防止二次封装
        if (body instanceof ResultWrapper) {
            return body;
        }

        // 处理controller返回为字符串时, 转换报异常的bug（默认使用的jackson转换器会报类型转换的错，感兴趣可以跟下源代码）
        //（如果使用FastJsonHttpMessageConverter，则不需要加下面if判断）
        if (body instanceof String) {
            return JsonUtils.toJsonString(ResultWrapper.ok(body));
        }

        return ResultWrapper.ok(body);

    }
}
