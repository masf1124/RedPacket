package com.masf.redpacket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: com.masf.redpacket.controller
 * @author: mashifei
 * @create: 2019-06-06-10
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/grab")
    public String login(ModelAndView mv){
        System.out.println("login");
        return "grab";
    }
}
