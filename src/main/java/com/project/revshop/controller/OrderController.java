package com.project.revshop.controller;

import com.project.revshop.entity.Order;
import com.project.revshop.entity.OrderItem;
import com.project.revshop.entity.SellerModel;
import com.project.revshop.entity.Cart;
import com.project.revshop.entity.UserModel;
import com.project.revshop.service.CartService;
import com.project.revshop.service.OrderService;
import com.project.revshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    
    @GetMapping
    public String checkOut(Model model,HttpSession session) {
    	int userid = (Integer)session.getAttribute("userId");
    	UserModel userModel = userService.getUserId(userid);
    	List<Cart> cartItems = cartService.getCartItemsByuserModel(userModel);
    	double total = 0;
    	for(Cart ct:cartItems) {
    		total += (ct.getQuantity()*ct.getProduct().getDiscountPrice());
    	}
    	model.addAttribute("total", total);

    	
    	model.addAttribute("cartItems",cartItems);
    	return "checkOut";
    }
    
    @PostMapping("/place")
    public String placeOrder(@RequestParam String shippingAddress, 
                             @RequestParam String billingAddress, 
                             HttpSession session, 
                             Model model) {
    	int userid = (Integer)session.getAttribute("userId");
    	UserModel user = userService.getUserId(userid);
        if (user == null) {
            return "redirect:/api/v1/login";
        }

        List<Cart> cartItems = cartService.getCartItemsByuserModel(user);
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "checkOut";
        }

        Order order = orderService.createOrder(user, cartItems, shippingAddress, billingAddress);
        cartService.clearCart(user);
        List<OrderItem> orderItem = orderService.getOrderItemsByOrder(order);
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("order", order);
        return "orderConfirmation";
    }

    @GetMapping("/history")
    public String orderHistory(HttpSession session, Model model) {
    	int userid = (Integer)session.getAttribute("userId");
    	UserModel user = userService.getUserId(userid);
        if (user == null) {
            return "redirect:/api/v1login";
        }

        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "orderHistory";
    }
    
    @GetMapping("/orderHistory")
    public String orderHistoryBySeller(HttpSession session, Model model) {
    	int sellerid = (Integer) session.getAttribute("sellerid");
		SellerModel sellerModel = userService.getSellerId(sellerid);
        if (sellerModel == null) {
            return "redirect:/api/v1/login";
        }

        List<Order> orders = orderService.getOrdersForSeller();
        model.addAttribute("orders", orders);
        return "orderHistoryForSeller";
    }
    
    @GetMapping("/details")
    public String orderDetails(@RequestParam("orderId") int orderId, Model model) {
    	Order order = orderService.getOrderById(orderId);
    	List<OrderItem> orderItems = orderService.getOrderItemsByOrder(order);
    	model.addAttribute("orderItems", orderItems);
    	return "orderDetails";
    }
}