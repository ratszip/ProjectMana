package com.thf.common;

import com.thf.common.oo.Res;
import com.thf.common.oo.ResultVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 自定义异常处理
 * @author: DT
 * @date: 2021/4/19 21:51
 * @version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultVO bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return Res.res(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return Res.res(5000,"空指针错误");
    }

    /**
     * 处理token异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest req, ExpiredJwtException e){
        logger.error("token过期！原因是:",e);
        return Res.res(40002,"token过期");
    }

    /**
     * 处理token异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = UnsupportedJwtException.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest req, UnsupportedJwtException e){
        logger.error("token不合法！原因是:",e);
        return Res.res(40003,"token不合法");
    }
}

