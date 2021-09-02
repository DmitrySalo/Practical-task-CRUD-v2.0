package com.example.webappcrud.controllers;

import com.example.webappcrud.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserDetailsService service;

    /*@Autowired
    public UserController(UserDetailsService service) {
        this.service = service;
    }*/

    @GetMapping
    public String getUserPage(@AuthenticationPrincipal User user, Model model) {
        Optional<UserDetails> userOptional =
                Optional.ofNullable(service.loadUserByUsername(user.getLogin()));

        if (userOptional.isPresent()) {
            model.addAttribute("user", (User) userOptional.get());
            return "user/user";
        }

        return "errors/not_found";
    }
}