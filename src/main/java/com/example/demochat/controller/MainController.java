package com.example.demochat.controller;

import com.example.demochat.entity.Users;
import com.example.demochat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepository messageRepository;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal Users users) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", users);
        data.put("messages", messageRepository.findAll());

        model.addAttribute("frontendData", data);

        return "index";
    }
}
