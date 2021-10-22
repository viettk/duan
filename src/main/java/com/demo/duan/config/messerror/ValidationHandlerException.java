package com.demo.duan.config.messerror;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandlerException extends ResponseEntityExceptionHandler {

    /* Bắt lỗi trong input: @notNull, @notBlank,... */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String mess = error.getDefaultMessage();
            errors.put(fieldName, mess);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    /* Bắt lỗi trong logic - trong serviceImpl */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyRunException(Exception ex, WebRequest request){
        String errorMess = ex.getLocalizedMessage();
        if(errorMess == null){
            errorMess = ex.toString();
        }
        MessError errorMessage = new MessError(errorMess.toString());
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
