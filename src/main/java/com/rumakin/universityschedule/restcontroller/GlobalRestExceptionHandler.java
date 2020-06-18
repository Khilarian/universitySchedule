package com.rumakin.universityschedule.restcontroller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rumakin.universityschedule.dto.ValidationError;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger loggerApiException = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
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
