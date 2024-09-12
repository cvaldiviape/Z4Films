package com.shared.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shared.utils.ObjectMapperUtil;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handlerAllExceptions(Exception exception, WebRequest request) throws JsonProcessingException {
        if (exception instanceof Z4GreedMoviesException ex) {
            ResponseDto response = ResponseUtil.error(ex.getMessage(), ex.getCode(), ex.getType());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException feignException) {
        try {
            String json = feignException.contentUTF8();
            ResponseDto responseDto = ObjectMapperUtil.convertJsonToObject(json);
            return new ResponseEntity<>(responseDto,  HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e1) {
            return new ResponseEntity<>(ResponseUtil.error(feignException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}