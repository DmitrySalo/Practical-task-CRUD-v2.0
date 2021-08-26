package com.example.webappcrud.controllers;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;
import com.example.webappcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("admin", service.showAll());
        return "admin/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<User> userOptional = Optional.ofNullable((service.showById(id)));

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "admin/show";
        }

        return "errors/not_found";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "ADMIN", required = false) String ADMIN,
                         @RequestParam(value = "USER", required = false) String USER) {

        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        setRolesIf(user, ADMIN, USER);
        service.createPerson(user);
        return "redirect:/admin";
    }

    private void setRolesIf(@ModelAttribute("user") @Valid User user,
                            @RequestParam(value = "ADMIN", required = false) String ADMIN,
                            @RequestParam(value = "USER", required = false) String USER) {
        Set<Role> roles = new HashSet<>();

        if (ADMIN != null) {
            roles.add(new Role(1, ADMIN));
        }

        if (USER != null) {
            roles.add(new Role(2, USER));
        }

        if ((ADMIN == null) && (USER == null)) {
            roles.add(new Role(2, USER));
        }

        user.setRoles(roles);
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", service.showById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(name = "ADMIN", required = false) String ADMIN,
                         @RequestParam(name = "USER", required = false) String USER) {

        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        setRolesIf(user, ADMIN, USER);
        service.updatePerson(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/admin";
    }
}