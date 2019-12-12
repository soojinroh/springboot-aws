package com.springbootaws.book.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("number", 1);
        return mav;
    }
}
