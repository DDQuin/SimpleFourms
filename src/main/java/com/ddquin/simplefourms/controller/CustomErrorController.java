package com.ddquin.simplefourms.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
             if(statusCode == HttpStatus.FORBIDDEN.value()) {
                String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
                model.addAttribute("errors", message);
                return "error-403";
            }
        }
        return "error";
    }

}
