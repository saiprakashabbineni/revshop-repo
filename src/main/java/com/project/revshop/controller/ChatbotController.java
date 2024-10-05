package com.project.revshop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.revshop.entity.Order;
import com.project.revshop.entity.UserModel;
import com.project.revshop.service.ChatbotService;
import com.project.revshop.service.OrderService;
import com.project.revshop.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/message")
    public Map<String, String> getMessage(@RequestBody Map<String, String> request,HttpSession session) {
        String userMessage = request.get("message");
        int userid = (Integer)session.getAttribute("userId");
        UserModel user = userService.getUserId(userid);
        String response = chatbotService.getResponse(userMessage,user);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("response", response);
        return responseBody;
    }
}
