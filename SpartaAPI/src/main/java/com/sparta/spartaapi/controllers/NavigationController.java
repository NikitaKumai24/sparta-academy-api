package com.sparta.spartaapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/web/courses")
    public String courses() {
        return "courses";
    }

    @GetMapping("/web/trainers")
    public String trainers() {
        return "trainers";
    }

    @GetMapping("/web/trainees")
    public String trainees() {
        return "trainees";
    }
}