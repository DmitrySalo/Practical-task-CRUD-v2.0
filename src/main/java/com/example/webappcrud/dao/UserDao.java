package com.example.webappcrud.dao;

import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;

import java.util.List;

public interface UserDao {

    List<User> showAll();

    User showById(int id);

    void createUser(User user);

    void updateUser(User user);

    void deleteById(int id);

    User getUserByName(String name);

    List<Role> showRoles();
}