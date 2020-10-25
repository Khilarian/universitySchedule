package com.rumakin.universityschedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/university")
    public String getStartPage() {
        return "university";
    }

}
