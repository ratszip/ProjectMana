package com.thf.common.utils;

import com.thf.common.oo.ResultVO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理标注了 @Validated 的类的方法调用参数校验失败导致的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("|"));
        return new ResultVO(4000,"参数校验失败");
    }

    // 处理表单类型请求普通参数缺失导致的异常
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResultVO handleServletRequestBindingException(ServletRequestBindingException e) {
        String message = e.getMessage();
        return new ResultVO(4000,"缺少参数");
    }

    // 处理表单类型请求的复杂参数校验失败导致的异常
    @ExceptionHandler(BindException.class)
    public ResultVO handleBindException(BindException e) {
        String message = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("|"));
        return new ResultVO(4000,"参数校验失败");
    }

    // 处理 application/json 类型请求的参数校验失败导致的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("|"));
        return new ResultVO(4000,"参数校验失败");
    }

}

