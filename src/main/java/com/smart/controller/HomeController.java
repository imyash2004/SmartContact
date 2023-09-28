package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("title","Home -Smart Contact Manager");
        return "home";
    }


    @GetMapping("/about")
    public String about(Model m){
        m.addAttribute("about","About -Smart Contact Manager");
        return "about";
    }
    @GetMapping("/failed")
    public String failed(Model m){
        m.addAttribute("failed","Failed -Smart Contact Manager");
        return "failed";
    }
    @GetMapping("/signin")
    public String login(Model m)
    {
        m.addAttribute("title","login");

        return "login";
    }


    @GetMapping("/signup")
    public String signup(Model m){
        m.addAttribute("title","Register -Smart Contact Manager");
        m.addAttribute("user",new User());
        return "signup";




    }

    @PostMapping("/do_register")
    public String registerUser( @Valid @ModelAttribute("user") User user ,BindingResult result1, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model m,HttpSession session){

        try {
            if(!agreement){
                System.out.println("you have not agreed the terms and condition");
                throw new Exception("you have not agreed the terms and condition");
            }
            if(result1.hasErrors()){
                System.out.println("ERROR"+result1.toString());
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            System.out.println(agreement);
            System.out.println(user);
            User r;
            r = this.userRepository.save(user);
            m.addAttribute("user",new User());
            session.setAttribute("message",new Message("SuccessFully Registered","alert-success"));

        }
        catch (Exception e){
            e.printStackTrace();
            m.addAttribute("user",new User());
            session.setAttribute("message",new Message("Some thing went wrong  "+e.getMessage(),"alert-danger"));
        }
        return "signup";
    }


}
