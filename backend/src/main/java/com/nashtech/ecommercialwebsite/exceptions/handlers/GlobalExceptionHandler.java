package com.nashtech.ecommercialwebsite.exceptions.handlers;


import com.nashtech.ecommercialwebsite.dto.response.ErrorResponse;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
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

    @ExceptionHandler({IllegalStateException.class})
    protected ResponseEntity<ErrorResponse> handleResourceIllegalException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse("409", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
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
