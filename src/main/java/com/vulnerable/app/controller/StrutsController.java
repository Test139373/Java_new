package com.vulnerable.app.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/struts")
public class StrutsController {

    @GetMapping("/ognl")
    public String ognlInjection(@RequestParam String expression) {
        // VULNERABLE: CVE-2017-5638 - Struts OGNL Injection
        ActionSupport action = new ActionSupport() {
            @Override
            public String execute() throws Exception {
                return SUCCESS;
            }
        };
        
        try {
            Object result = action.getText(expression);
            return "OGNL Expression result: " + result;
        } catch (Exception e) {
            return "Error evaluating OGNL: " + e.getMessage();
        }
    }
}