package com.project.revshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.revshop.model.Review;
import com.project.revshop.service.ReviewService;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{id}")
	public String displayCommentForm(Model model, @PathVariable int id) {
		model.addAttribute("review", new Review());
		model.addAttribute("productId", id);
		return "productDetails";
	}
	
//	@PostMapping("/{id}")
//	public String addComment(@ModelAttribute Review review, @PathVariable int id, Model model) {
//		Product product = productService.getProduct(id);
//		review.setPost(product);
//		reviewService.addReview(review);
//		return "posts";
//	}
}
