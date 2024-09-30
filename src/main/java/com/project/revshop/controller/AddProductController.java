package com.project.revshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.revshop.model.Category;
import com.project.revshop.model.Product;
import com.project.revshop.model.Size;
import com.project.revshop.service.ProductService;

@Controller
@RequestMapping("/api/v1")
public class AddProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/sizes")
	@ResponseBody
	public List<Size> getAllSizes(@RequestParam("categoryId") int categoryId) {
	    List<Size> sizes = productService.getAllSizes(categoryId);
	    return sizes;
	}
	
	@GetMapping("/update")
	public String showUpdateForm(@RequestParam("productId") int productId,Model model) {
		Product product = productService.getProductDetails(productId);
		List<Category> categories = productService.getAllCategories();
		model.addAttribute("categories",categories);
		model.addAttribute("product",product);
		return "updateProductToInventory";
	}
	
	@GetMapping("/addForm")
	public String showProductPage(Model model) {
		model.addAttribute("Product", new Product());
		List<Category> categories = productService.getAllCategories();
		model.addAttribute("categories",categories);
		return "addProductsToInventory";
	}
	
	@PostMapping("/update")
	public String updateProductToInventory(@ModelAttribute Product product,Model model,@RequestParam("productId") int productId) {
		product.setProductId(productId);
		productService.saveProduct(product);
		return "displayProducts";
	}
	
	@PostMapping("/add")
	public String addProductToInventory(@ModelAttribute Product product, Model model,@RequestParam("size") int sizeId ) {
		Size size = productService.getSizeById(sizeId);
		product.setSize(size);
		productService.saveProduct(product);
		return "displayProducts";
	}
	
//	@DeleteMapping("/delete")
//	public String deleteProuctFromInventory(@RequestParam("productId") int productId) {
//		productService.deleteProduct(productId);
//		return "addProductsToInventory";
//	}
}
