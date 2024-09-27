package com.project.revshop.controller;

import com.project.revshop.entity.UserModel;
import com.project.revshop.entity.SellerModel;
import com.project.revshop.repository.SellerRepository;
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
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserModel user = new UserModel();
        SellerModel seller = new SellerModel();
        user.setSellermodel(seller); // Link it back to the UserModel
        model.addAttribute("user", user);
        return "Register"; 
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@ModelAttribute("user") UserModel userModel) {
        if ("seller".equals(userModel.getUserRole())) {
            SellerModel seller = userModel.getSellermodel();
            seller.setUsermodel(userModel); // Link the seller to the user

            // Save user first
            UserModel savedUser = userRepository.save(userModel);
            seller.setUsermodel(savedUser); // Link saved user back to seller
            sellerRepository.save(seller); // Save seller
        } else {
            userRepository.save(userModel); // Save regular user
        }

        return new ResponseEntity("Hello", HttpStatus.OK); // Redirect to a success page
    }
}
