package com.smart.controller;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.SQLOutput;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;

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
    public String processContact(@ModelAttribute Contact contact, Principal p, @RequestParam("pimage") MultipartFile image, HttpSession session) {
        try {
            String name =p.getName();
            User user=this.userRepository.getUserByUserName(name);



            if(image.isEmpty()){

                System.out.println("file is empty");
            }
            else{
                contact.setImage(image.getOriginalFilename() );
                File file=new ClassPathResource("static/images").getFile();
                Path path =Paths.get(file.getAbsolutePath()+File.separator+image.getOriginalFilename());

                Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("image is uploaded");
            }
            user.getContacts().add(contact);
            contact.setUser(user);
            this.userRepository.save(user);
            System.out.println("data:"+contact);
            session.setAttribute("message",new Message("your contact is added successfully","success"));


        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("error"+e.getMessage());

            session.setAttribute("message",new Message("something went wrong try again","alert"));

        }
        return "normal/add_contact_form";
    }
//per page =5n
    //current page =0
    @GetMapping("/show-contacts/{page}")
    public String showContacts( @PathVariable("page") Integer page,Model m,Principal p){
        String name=p.getName();
        User user=this.userRepository.getUserByUserName(name);
        Pageable pageable =PageRequest.of(page,1);
        Page<Contact> contacts=this.contactRepository.findContactsByUser(user.getId(),pageable);
        m.addAttribute("contacts",contacts);
        m.addAttribute("currentPage",page);
        m.addAttribute("totalPages",contacts.getTotalPages());
        return "normal/show-contacts";
    }

}
