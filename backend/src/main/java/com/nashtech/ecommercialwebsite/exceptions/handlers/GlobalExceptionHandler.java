package com.nashtech.ecommercialwebsite.exceptions.handlers;


import com.nashtech.ecommercialwebsite.dto.response.ErrorResponse;
import com.nashtech.ecommercialwebsite.exceptions.*;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("404", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceConfictException.class})
    protected ResponseEntity<ErrorResponse> handleResourceConflictException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("409", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<ErrorResponse> handleUnauthorizedException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("401", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("400", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InternalServerException.class})
    protected ResponseEntity<ErrorResponse> handleInternalServerException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("500", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse error = new ErrorResponse("400", "Validation Error", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
