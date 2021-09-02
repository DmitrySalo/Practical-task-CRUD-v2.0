package com.example.webappcrud.controllers;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;
import com.example.webappcrud.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    @GetMapping
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("user", user);
        return "admin/admin";
    }

    @GetMapping("/new")
    public String newUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(required = false, name = "newRoles") String[] newRoles) {

        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        setRoles(user, newRoles);
        service.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "admin/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(required = false, name = "currentRoles") String[] currentRoles) {

        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }

        setRoles(user, currentRoles);
        service.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.deleteUserById(id);
        return "redirect:/admin";
    }

    private void setRoles(@ModelAttribute("user") @Valid User user,
                          @RequestParam(required = false, name = "currentRoles") String[] currentRoles) {
        Set<Role> userRoles = new HashSet<>();
        if (currentRoles != null) {
            for (String role : currentRoles) {
                userRoles.add(service.getRoleByName(role));
            }
        }

        if (userRoles.isEmpty()) {
            userRoles.add(service.getRoleByName("ROLE_USER"));
        }

        user.setRoles(userRoles);
    }
}