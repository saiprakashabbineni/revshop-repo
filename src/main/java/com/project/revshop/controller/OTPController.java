package com.project.revshop.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.revshop.entity.LoginModel;
import com.project.revshop.service.EmailService;
import com.project.revshop.service.OTPService;
import com.project.revshop.service.UserService;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/api/v1")
public class OTPController {
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		return "forgotPassword";
	}
	
	@PostMapping("/generate-otp")
	public String generateOTP(@RequestParam("email") String key, Model model) {
		if(!key.isEmpty()) {
			int otp = otpService.generateOTP(key);
			model.addAttribute("email", key);
			System.out.println(otp);
			try {
				emailService.sendOTPMessage(key, "OneTimePassword to change password", "Expire Time: 5 mins and OTP: " + String.valueOf(otp));
			} catch (MessagingException e) {
				e.printStackTrace();
				model.addAttribute("isEmailSent", false);
				return "forgotPassword";
			}
		}
		return "forgotPassword";			
	}
	
	@PostMapping("/validate-otp")
	public String validateOTP(@RequestParam("one") int one, @RequestParam("two") int two, @RequestParam("three") int three, 
			@RequestParam("four") int four, @RequestParam("five") int five, @RequestParam("six") int six,
			@RequestParam("email") String key, Model model) {
		List<Integer> list = new ArrayList<>();
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		list.add(five);
		list.add(six);
		
		int enteredOTP = 0;
		for(Integer i: list) {
			enteredOTP = enteredOTP * 10 + i;
		}
		
		boolean isOTPValid = otpService.validateOTP(key, enteredOTP);
		if(isOTPValid) {
			otpService.clearOTP(key);
			model.addAttribute("key", key);
			return "showChangePassword";
		}
		else {
			model.addAttribute("isOTPValid", isOTPValid);
			return "forgotPassword";
		}
	}
	
	@PostMapping("/update-password")
	public String updatePassword(@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("key") String key, Model model) {
		System.out.println(password + " " + confirmPassword + " " + key);
		userService.updatePassword(password, key);
		model.addAttribute("login", new LoginModel());
		return "Login";
	}
		
}
