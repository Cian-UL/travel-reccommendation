package com.cian.travel_recommendation.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Global exception handler for all controllers
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation constraint violations from @Min and similar annotations
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException ex, Model model) {

        model.addAttribute("error", "Please enter valid search values. Budget, duration and travelers must all be at least 1.");

        return "error";
    }

    // Catch-all for any other exceptions
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {

        model.addAttribute("error", "Something went wrong. Please try again.");

        return "error";
    }
}