package com.rumakin.universityschedule.restcontroller;

import java.util.*;
import java.util.stream.Collectors;


import org.slf4j.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

import com.rumakin.universityschedule.dto.ValidationError;
import com.rumakin.universityschedule.exception.InvalidEntityException;
import com.rumakin.universityschedule.exception.ResourceNotFoundException;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger loggerApiException = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        loggerApiException.error("{} - '{}'", e.getClass().getSimpleName(), e.getMessage());
        HttpStatus status = INTERNAL_SERVER_ERROR;
        String msg = "";
        if (e instanceof ResourceNotFoundException) {
            msg = e.getMessage();
            status = NOT_FOUND;
        } else if (e instanceof InvalidEntityException) {
            msg = e.getMessage();
            status = BAD_REQUEST;
        } else {
            msg = e.getMessage();
        }
        return new ResponseEntity<>(msg, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        loggerApiException.warn("{} - '{}'", e.getClass().getSimpleName(), e.getBindingResult());
        List<ValidationError> body = e.getBindingResult().getFieldErrors().stream()
                .map(x -> new ValidationError(x.getField(), x.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
