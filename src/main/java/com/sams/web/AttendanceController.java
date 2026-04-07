package com.sams.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}

