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
        model.addAttribute("ddd","KKKKKKK");
        String view = "home/index";
        return view;
    }

}
