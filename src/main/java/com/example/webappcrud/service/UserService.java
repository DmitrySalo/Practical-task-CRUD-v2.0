package com.example.webappcrud.service;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByLogin(String login);

    Role getRoleByName(String roleName);

    void createUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    List<Role> getAllRoles();
}