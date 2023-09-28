package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserRepository userRepository;
    @RequestMapping("/index")
    public String dashboard(Model m, Principal principal){
        String username=principal.getName();
        System.out.println(username+"Username");
        User user=userRepository.getUserByUserName(username);
        System.out.println(user);
        m.addAttribute("user",user);
        return "normal/user_dashboard";
    }
}
