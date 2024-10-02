package com.project.revshop.controller;

import com.project.revshop.entity.Cart;
import com.project.revshop.entity.Product;
import com.project.revshop.entity.SellerModel;
import com.project.revshop.entity.UserModel;
import com.project.revshop.service.CartService;
import com.project.revshop.service.ProductService;
import com.project.revshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String showCartPage(Model model,HttpSession session) {
    	int userid = (Integer)session.getAttribute("userId");
    	UserModel userModel = userService.getUserId(userid);
    	List<Cart> cartItems = cartService.getCartItemsByuserModel(userModel);
//    	System.out.println(cartItems);
    	model.addAttribute("cartItems",cartItems);
    	return "showCart";
    	
    }
    
    @PostMapping
    public String addToCart(Model model,@ModelAttribute Cart cart,@RequestParam("productId") int productId,@RequestParam("quantity") int quantity,HttpSession session) {
    	Product product = productService.getProductById(productId);
    	cart.setProduct(product);
    	cart.setQuantity(quantity);
    	int userid = (Integer)session.getAttribute("userId");
    	UserModel userModel = userService.getUserId(userid);
    	cart.setUser(userModel);
    	cartService.addToCart(cart);
    	List<Cart> cartItems = cartService.getCartItemsByuserModel(userModel);
    	model.addAttribute("cartItems",cartItems);
    	return "showCart";
    }
    
    @DeleteMapping
    public String deleteFromCart(HttpSession session, @RequestParam("productId") Integer productId) {
    	Integer userId = (Integer) session.getAttribute("userId");
    	Cart cart = cartService.findByUserAndProduct(userId, productId);
    	cartService.deleteFromCart(cart);
    	return "redirect:/api/v1/cart";
    }
    
    @PutMapping
    public String updateQuantity(HttpSession session, @RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
    	Integer userId = (Integer) session.getAttribute("userId");
    	Cart cart = cartService.findByUserAndProduct(userId, productId);
    	if(quantity > 0) {    		
    		cart.setQuantity(quantity);
    		cartService.updateCart(cart);
    	}
    	else {
    		cartService.deleteFromCart(cart);
    	}
    	return "redirect:/api/v1/cart";
    }
}
