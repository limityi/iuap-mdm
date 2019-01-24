package com.yonyou.iuap.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/index")
    public String home(HttpServletRequest request, Model model) {
        String view = "WEB-INF/views/home/index";
        return view;
    }

    @RequestMapping("/data")
    public String data(HttpServletRequest request, Model model) {
        String view = "data";
        return view;
    }

    @RequestMapping("/monitor")
    public String monitor(HttpServletRequest request, Model model) {
        String view = "monitor";
        return view;
    }

}
