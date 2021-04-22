package com.islandcollaborative.creativeexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * Responsible for handling all errors and throw especial exceptions
     * for some HTTP status codes. Otherwise, it will return a map that
     * ultimately will be converted to a json error.
     */
    @GetMapping(ERROR_PATH)
    public String handleErrors(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        if (status != null) {
            if (status.equals(HttpStatus.NOT_FOUND)) {
                return "error404";
            } else if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                return "error500";
            }
        }
        return "error500";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
