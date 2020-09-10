package com.byteobject.prototype.springsecurity2.controller.advice;

import com.byteobject.prototype.springsecurity2.service.department.DepartmentNotFoundException;
import com.byteobject.prototype.springsecurity2.service.person.PersonNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ControllerExceptionAdvice {

    private final MessageSource messageSource;

    @Autowired
    public ControllerExceptionAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetailsDTO translatePersonNotFoundException(PersonNotFoundException e) {
        return getErrorDetails(e.getErrorCode(), e.getArgs());
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetailsDTO translateDepartmentNotFoundException(DepartmentNotFoundException e) {
        return getErrorDetails(e.getErrorCode(), e.getArgs());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetailsDTO translateAccessDeniedException(AccessDeniedException e) {
        ErrorDetailsDTO errorDetailsDTO = getErrorDetails("unauthorized_access", Arrays.asList());

        return errorDetailsDTO;
    }

    @NotNull
    private ErrorDetailsDTO getErrorDetails(String errorCode, List<Object> args) {
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
        errorDetailsDTO.setError(errorCode);
        errorDetailsDTO.setTimestamp(new Date());
        errorDetailsDTO.setMessage(messageSource.getMessage(errorCode, args.toArray(), Locale.getDefault()));

        return errorDetailsDTO;
    }
}
