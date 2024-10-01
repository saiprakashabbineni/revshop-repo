package com.project.revshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.project.revshop.entity.Product;
import com.project.revshop.enums.Gender;
import com.project.revshop.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/v1")
public class FilterProductController {
	
	@Autowired
	private ProductService productService;
	
	
	
@GetMapping("/allproducts")

public String showAllProducts(Model model ) {
	
	List<Product> fetchedproducts = productService.getAllProducts();
	
	System.out.println(fetchedproducts);
	
	model.addAttribute("fetchedproducts",fetchedproducts);
	
	return "allProducts";	
}

//@GetMapping("/allfetchedproducts")
//
//public String showProductsByGender(@RequestParam Gender gender, Model model) {
//	
//	List<Product> fetchedproducts;
//	
//	if(gender == Gender.Other) {
//		
//		fetchedproducts = productService.getAllProducts();
//		
//	}else {
//		
//		 fetchedproducts = productService.getAllProductsByGender(gender);
//	}
//	
//	model.addAttribute("fetchedproducts",fetchedproducts);
//
//	return "allProducts";	
//}

@GetMapping("/allfetchedproducts")
public String showFilteredProducts(@RequestParam(required = false) List<String> gender,
                                    @RequestParam(required = false) List<String> price,
                                    Model model) {
	
//	 Integer userId = (Integer) session.getAttribute("userId");
//     Integer sellerId = (Integer) session.getAttribute("sellerid");
//     
//     System.out.println("from flter userId" + userId);
//     System.out.println("from flter sellerid" + sellerId);

     
     
    List<Product> fetchedProducts;
    double minPrice = 0.0;
    double maxPrice = Double.MAX_VALUE;

    if (price != null) {
        if (price.contains("under2000")) {
            maxPrice = 2000.00;
        }
        if (price.contains("2000to3500")) {
            minPrice = Math.max(minPrice, 2000.00);
            maxPrice = Math.min(maxPrice, 3500.00);
        }
        if (price.contains("3500to6000")) {
            minPrice = Math.max(minPrice, 3500.00);
            maxPrice = Math.min(maxPrice, 6000.00);
        }
        if (price.contains("above6000")) {
            minPrice = Math.max(minPrice, 6000.00);
        }
    }

    List<Gender> selectedGenders = (gender != null && !gender.isEmpty()) 
            ? gender.stream()
                    .map(Gender::valueOf)
                    .collect(Collectors.toList()) 
            : List.of(); 

    if (!selectedGenders.isEmpty()) {
        fetchedProducts = productService.getProductsByGenderInAndPriceBetween(selectedGenders, minPrice, maxPrice);
    } else {
        fetchedProducts = productService.getProductsByPriceBetween(minPrice, maxPrice);
    }

    model.addAttribute("fetchedproducts", fetchedProducts);
    return "allProducts";
}

}



