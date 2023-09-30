package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserRepository userRepository;
    @ModelAttribute
    public void addCommonData(Model m,Principal p){

        String username=p.getName();

        User user=userRepository.getUserByUserName(username);

        m.addAttribute("user",user);
    }
    @RequestMapping("/index")
    public String dashboard(Model m, Principal principal){
//        String username=principal.getName();
//        System.out.println(username+"Username");
//        User user=userRepository.getUserByUserName(username);
//        System.out.println(user);
//        m.addAttribute("user",user);
        m.addAttribute("title","user_dashboard");
        return "normal/user_dashboard";
    }

    //open add form handler
    @GetMapping("/add_contact")
    public String openAddContactForm(Model m){
        m.addAttribute("title","Add Contact");
        m.addAttribute("contact",new Contact());

        return "normal/add_contact_form";
    }


    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("image") MultipartFile image) {
        // Handle the uploaded file (image) here
        // You can access the file content using image.getBytes() or process it as needed

        // Process other fields in the Contact object

        // Rest of your method logic

        System.out.println(contact);
        return "normal/add_contact_form";
    }

}
