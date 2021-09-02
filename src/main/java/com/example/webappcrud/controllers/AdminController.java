package com.example.webappcrud.controllers;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;
import com.example.webappcrud.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;

    /*@Autowired
    public AdminController(UserService service) {
        this.service = service;
    }*/

    @GetMapping
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("user", user);
        return "admin/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<User> userOptional = Optional.ofNullable((service.getUserById(id)));

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "admin/show";
        }

        return "errors/not_found";
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
                         /*@RequestParam(value = "ADMIN", required = false) String ADMIN,
                         @RequestParam(value = "USER", required = false) String USER*/
                         @RequestParam(required = false, name = "newRoles") String[] newRoles) {

        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        setRoles(user, newRoles);
        //setRolesIf(user, ADMIN, USER);
        service.createUser(user);
        return "redirect:/admin";
    }

    /*private void setRolesIf(@ModelAttribute("user") @Valid User user,
                            @RequestParam(value = "ADMIN", required = false) String ADMIN,
                            @RequestParam(value = "USER", required = false) String USER) {
        Set<Role> newRoles = new HashSet<>();

        if (ADMIN != null) {
            newRoles.add(new Role(1, ADMIN));
        }

        if (USER != null) {
            newRoles.add(new Role(2, USER));
        }

        if ((ADMIN == null) && (USER == null)) {
            newRoles.add(new Role(2, USER));
        }

        user.setRoles(newRoles);
    }*/

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         /*@RequestParam(name = "ADMIN", required = false) String ADMIN,
                         @RequestParam(name = "USER", required = false) String USER*/
                         @RequestParam(required = false, name = "currentRoles") String[] currentRoles) {

        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }

        setRoles(user, currentRoles);
        //setRolesIf(user, ADMIN, USER);
        service.updateUser(user);
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

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.deleteUserById(id);
        return "redirect:/admin";
    }
}