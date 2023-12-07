package com.xiang.handler.exception;

import com.xiang.domain.ResponseResult;
import com.xiang.enums.AppHttpCodeEnum;
import com.xiang.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException systemException) {
        //打印异常信息
        log.error("出现了异常！ {}",systemException);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(systemException.getCode(),systemException.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
