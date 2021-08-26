package com.example.webappcrud.service;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;

import java.util.List;

public interface UserService {

    List<User> showAll();

    User showById(int id);

    void createPerson(User user);

    void updatePerson(User user);

    void deleteById(int id);

    List<Role> showRoles();
}