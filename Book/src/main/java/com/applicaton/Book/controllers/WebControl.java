package com.applicaton.Book.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebControl {

    @GetMapping("/nav/")
    public String toBasket() {
        return "index";
    }

    @GetMapping("/nav/ingegralService")
    public String toMain() {
        return "ingegralService";
    }
}