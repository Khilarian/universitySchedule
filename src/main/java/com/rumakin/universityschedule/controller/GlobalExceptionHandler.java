package com.rumakin.universityschedule.controller;

import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.rumakin.universityschedule.exception.ResourceNotFoundException;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(Exception e) {
        logger.error("{} - '{}'", e.getClass().getSimpleName(), e.getMessage());
        ModelAndView model = new ModelAndView("/common/notfound");
        model.addObject("errorMessage", e.getMessage());
        return model;
    }

}
