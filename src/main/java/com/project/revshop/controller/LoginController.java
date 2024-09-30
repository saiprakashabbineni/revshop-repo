package com.project.revshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.revshop.entity.LoginModel;
import com.project.revshop.entity.SellerModel;
import com.project.revshop.entity.UserModel;
import com.project.revshop.repository.UserRepository;
import com.project.revshop.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/v1/login")
public class LoginController {

	@Autowired
	private UserService userservice;

	@GetMapping
	public String showLoginForm(Model model) {
		model.addAttribute("login", new LoginModel());
		return "Login";

	}

	@PostMapping
	public ResponseEntity<String> processLogin(@ModelAttribute("login") LoginModel loginModel, HttpSession session) {
		UserModel user = userservice.validateLogin(loginModel.getUserEmail(), loginModel.getUserPassword());

		if (user != null) {

			Integer fetcheduserId = (user.getUserId());
			
			System.out.println("fetcheduser ID: " + fetcheduserId);

			session.setAttribute("userId", fetcheduserId);

			System.out.println("Login successful! Welcome, " + user.getUserEmail());

			if ("buyer".equals(user.getUserRole())) {

				return ResponseEntity.ok("redirect:/buyerDashboard");
				
			} else {

				SellerModel seller = userservice.getSellerId(fetcheduserId);

				if (seller != null) {
					int sellerId = seller.getSellerId();
					session.setAttribute("sellerid", sellerId);

					System.out.println("Seller ID: " + sellerId);

					return ResponseEntity.ok("redirect:/sellerDashboard");

				} else {
					System.out.println("No seller found for user ID: " + fetcheduserId);

					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No seller found.");
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
		}
	}

}
