package com.project.revshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.revshop.entity.Order;
import com.project.revshop.entity.OrderItem;
import com.project.revshop.entity.UserModel;
import com.project.revshop.repository.OrderItemRepository;
import com.project.revshop.repository.OrderRepository;




@Service
public class ChatbotService {
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	
	 public String getResponse(String message,UserModel user) {
		 	String response;
		 	String name ;
		 	String result="";
	        message = message.trim().toLowerCase();
	        if(message.contains("hi")||message.contains("hello")) {
	        	return "Hello! " + user.getUserName()+" How can I assist you today?";
	        }
	        else if (message.contains("hello") || message.contains("hi")) {
	            response = "Hello! How can I assist you today?";
	        } else if (message.contains("order status")) {
	            response = "Please provide your order ID so I can check the status for you.";
	        } else if (message.contains("return") || message.contains("refund")) {
	            response = "You can return or request a refund by visiting the 'My Orders' section and selecting the appropriate option.";
	        } else if (message.contains("shipping")) {
	            response = "Our standard shipping time is 3-5 business days. Expedited shipping is available at an additional cost.";
	        } else if (message.contains("payment")) {
	            response = "We accept various payment methods including credit/debit cards, PayPal, and net banking.";
	        } else if (message.contains("thank you") || message.contains("thanks")) {
	            response = "You're welcome! If you have any more questions, feel free to ask.";
	        } else if(Character.isDigit(message.charAt(0))) {
	        	int orderId = Integer.parseInt(message);
	        	Order order = orderRepository.findById(orderId).get();
	        	List<OrderItem> orderitem = orderItemRepository.findByOrder(order);
	        	for(OrderItem ordit:orderitem) {
	        		name = ordit.getProduct().getName();
	        		result += "Your Order with Product Name: "+name+" Order Status "+ order.getOrderStatus();
	        	}
	        	return result;
	        }
	        
	        else {
	            response = "I'm sorry,Can you call CustomerCare: 7396728042";
	        }

	        return response;
	    }
}
