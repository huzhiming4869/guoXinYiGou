package com.xcy.gxyg.common.exception;

import com.xcy.gxyg.common.res.Res;
import com.xcy.gxyg.common.res.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Ming
 */
@Slf4j
@RestControllerAdvice
public class UnifiedExceptionhandler {

    @ExceptionHandler(value = Exception.class)
    public Res handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Res.error();
    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Res handleException(BadSqlGrammarException e) {
        log.error(e.getMessage(), e);
        return Res.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }


    @ExceptionHandler(value = BusinessException.class)
    public Res handleException(BusinessException e) {
        log.error(e.getMessage());
        return Res.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Res handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        //SERVLET_ERROR(-102, "servlet请求异常"),
        return Res.error().message(ResponseEnum.SERVLET_ERROR.getMessage()).code(ResponseEnum.SERVLET_ERROR.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Res handleServletException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return Res.error().message(message);
    }
}