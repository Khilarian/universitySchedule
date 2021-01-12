package com.rumakin.universityschedule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    
    @PostMapping("/login-error")
    public String getLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    
}
