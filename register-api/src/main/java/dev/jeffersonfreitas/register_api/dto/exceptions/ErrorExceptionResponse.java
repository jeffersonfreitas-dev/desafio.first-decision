package dev.jeffersonfreitas.register_api.dto.exceptions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ErrorExceptionResponse(int code, String message, LocalDateTime timestamp, List<String> errors) {

    public static ErrorExceptionResponse create(int codigo, String message) {
        return new ErrorExceptionResponse(codigo, message, LocalDateTime.now(), Collections.emptyList());
    }

    public static ErrorExceptionResponse create(int codigo, String message, List<String> erros) {
        return new ErrorExceptionResponse(codigo, message, LocalDateTime.now(), erros);
    }
}
