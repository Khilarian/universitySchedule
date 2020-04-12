package com.rumakin.universityschedule.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/main_page")
    public String sayHello() {
        return "main_page";
    }

}
