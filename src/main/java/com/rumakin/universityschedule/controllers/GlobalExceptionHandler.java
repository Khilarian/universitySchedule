package com.rumakin.universityschedule.controllers;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("{} - '{}'", e.getClass().getSimpleName(), e.getMessage());
        ModelAndView model = new ModelAndView("/common/notfound");
        model.addObject("errorMessage", e.getMessage());
        return model;
    }
}
