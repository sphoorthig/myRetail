package com.target.myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleException(Exception ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                internalServerError.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, internalServerError);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleException(ProductNotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                notFound.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class})
    public final ResponseEntity<ErrorMessage> handleException(NumberFormatException ex) {
        HttpStatus notFound = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = new ErrorMessage("Invalid product id in path variable",
                notFound.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler({UpdateRequestNotValidException.class})
    public final ResponseEntity<ErrorMessage> handleException(UpdateRequestNotValidException ex) {
        HttpStatus notFound = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                notFound.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<ErrorMessage> handleException(IllegalArgumentException ex) {
        HttpStatus notFound = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(),
                notFound.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(errorMessage, notFound);
    }
}
