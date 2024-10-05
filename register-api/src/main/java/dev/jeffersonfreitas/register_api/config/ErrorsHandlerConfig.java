package dev.jeffersonfreitas.register_api.config;

import dev.jeffersonfreitas.register_api.dto.exceptions.ErrorExceptionResponse;
import dev.jeffersonfreitas.register_api.exceptions.AlreadyRegisteredException;
import dev.jeffersonfreitas.register_api.exceptions.BadRequestException;
import dev.jeffersonfreitas.register_api.exceptions.BusinessException;
import dev.jeffersonfreitas.register_api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorsHandlerConfig {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorExceptionResponse handlerBadRequestException(BadRequestException err) {
        return ErrorExceptionResponse.create(HttpStatus.BAD_REQUEST.value(), err.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorExceptionResponse handlerNotFoundException(NotFoundException err) {
        return ErrorExceptionResponse.create(HttpStatus.NOT_FOUND.value(), err.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyRegisteredException.class)
    public ErrorExceptionResponse handlerAlreadyRegisteredException(AlreadyRegisteredException err) {
        return ErrorExceptionResponse.create(HttpStatus.UNAUTHORIZED.value(), err.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        final var message = "Existe(m) erro(s) de validação";
        List<String> errors = ex.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
        return ErrorExceptionResponse.create(HttpStatus.BAD_REQUEST.value(), message, errors);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public ErrorExceptionResponse handlerBusinessException(BusinessException err) {
        return ErrorExceptionResponse.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), err.getMessage());
    }
}
