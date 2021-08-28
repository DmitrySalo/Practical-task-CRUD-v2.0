package com.example.webappcrud.service;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;

import java.util.List;

public interface UserService {

    List<User> showAll();

    User showById(int id);

    User showByLogin(String login);

    Role showRoleByName(String roleName);

    void createUser(User user);

    void updateUser(User user);

    void deleteById(int id);

    List<Role> showRoles();
}