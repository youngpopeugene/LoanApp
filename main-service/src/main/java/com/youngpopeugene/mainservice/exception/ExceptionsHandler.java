package com.youngpopeugene.mainservice.exception;

import com.youngpopeugene.mainservice.model.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    private ErrorResponse auth(AuthException e){
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ErrorResponse notFound(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidateException.class)
    private ErrorResponse validate(ValidateException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoMoneyException.class)
    private ErrorResponse noMoney(NoMoneyException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JobException.class)
    private ErrorResponse job(JobException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonConvertingException.class)
    private ErrorResponse parse(JsonConvertingException e) {
        return new ErrorResponse(e.getMessage());
    }
}