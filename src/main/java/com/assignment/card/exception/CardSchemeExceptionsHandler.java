package com.assignment.card.exception;

import com.assignment.card.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
@RestControllerAdvice
public class CardSchemeExceptionsHandler {


    @ExceptionHandler(value = {InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto arcGisException(InvalidDataException e) {
        return new ErrorDto(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(NotFoundException e) {
        return new ErrorDto(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto exception(Exception e) {
        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong!");
    }

}