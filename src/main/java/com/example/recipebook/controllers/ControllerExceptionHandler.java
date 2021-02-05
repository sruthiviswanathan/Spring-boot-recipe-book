package com.example.recipebook.controllers;

import com.example.recipebook.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ValidationException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Exception Occurred: " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("exceptionName", "404 Not Found");
        mav.addObject("error", exception.getMessage());
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Exception Occurred: " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("exceptionName", "400 Bad Request");
        mav.addObject("error", "NumberFormatException " + exception.getMessage() + ". Id should not be of type string");
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(Exception exception) {
        log.error("Exception Occurred: " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.addObject("exceptionName", "400 Bad Request");
        mav.addObject("error", "Validation Exception occurred: " + exception.getMessage());
        return mav;
    }
}
