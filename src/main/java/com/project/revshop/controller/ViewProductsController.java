package com.project.revshop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.revshop.entity.Product;
import com.project.revshop.service.ProductService;

@Controller
@RequestMapping
public class ViewProductsController {
	
	@Autowired
	private ProductService productService;
		
	@GetMapping("/api/v1/buyer-dashboard")
	public String viewDashboard() {
		return "buyerDashboard";
	}

	@GetMapping("/api/v1/products")
	public String viewProductsPage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "view001";
	}
	
	@GetMapping("/api/v1/products/{id}")
	public String viewProductsById(@PathVariable("id") int productId, Model model) {
		Product products = productService.getProductById(productId);
        model.addAttribute("product", products);
//        double averageRating = productService.getRating(productId);
//        System.out.println(averageRating);
//        model.addAttribute("averageRating", averageRating);
//     	model.addAttribute("selectedProduct", products);  
        return "ProductDetails"; 
	}	
}
