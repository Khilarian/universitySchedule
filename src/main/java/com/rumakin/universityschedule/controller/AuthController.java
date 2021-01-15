package com.rumakin.universityschedule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final String LOGIN_PAGE = "login";

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String getLoginPage() {
        logger.debug("getLogin()");
        return LOGIN_PAGE;
    }

    @PostMapping("/login-error")
    public String getLoginErrorPage(Model model) {
        logger.debug("getLoginErrorPage()");
        model.addAttribute("loginError", true);
        return LOGIN_PAGE;
    }

    @PostMapping("/access-denied")
    public String getAccessDeniedPage(Model model) {
        logger.debug("getAccessDeniedPAge()");
        model.addAttribute("accessDinied", true);
        return LOGIN_PAGE;
    }

}
