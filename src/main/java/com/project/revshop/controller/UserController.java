package com.project.revshop.controller;

import com.project.revshop.entity.UserModel;
import com.project.revshop.entity.SellerModel;
import com.project.revshop.repository.UserRepository;
import com.project.revshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/register")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showRegistrationForm(Model model) {
        UserModel user = new UserModel();
        SellerModel seller = new SellerModel();
        user.setSellermodel(seller);
        model.addAttribute("user", user);
        return "Register";
    }

    @PostMapping
    public ResponseEntity<String> processRegistration(
            @ModelAttribute("user") UserModel userModel) {
    	
    	
        if ("seller".equals(userModel.getUserRole())) {
            SellerModel seller = userModel.getSellermodel();
            if (seller != null) {
                seller.setUsermodel(userModel);
                UserModel savedUser = userRepository.save(userModel);
                seller.setUsermodel(savedUser); 
                userService.saveSeller(seller);
            } else {
                return new ResponseEntity<>("Seller details are missing", HttpStatus.BAD_REQUEST);
            }
        } else {
            userService.saveUser(userModel); 
        }
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
       
    }

   
}
