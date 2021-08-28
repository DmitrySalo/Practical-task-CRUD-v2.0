package com.example.webappcrud.service;

import com.example.webappcrud.dao.UserDao;
import com.example.webappcrud.dao.UserRepository;
import com.example.webappcrud.models.Role;
import com.example.webappcrud.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }*/

    @Override
    public List<User> showAll() {
        //return userDao.showAll();
        return userRepository.findAll();
    }

    @Override
    public User showById(int id) {
        //return userDao.showById(id);
        return userRepository.findById(id).get();
    }

    @Override
    public User showByLogin(String login) {
        //return userDao.showByLogin(login);
        return userRepository.findUserByLogin(login);
    }

    /*@Override
    public Role showRoleByName(String roleName) {
        return userDao.showRoleByName(roleName);
    }*/

    @Override
    public void createUser(User user) {
        //userDao.createUser(user);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //userDao.updateUser(user);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(int id) {
        //userDao.deleteById(id);
        userRepository.deleteById(id);
    }

    /*@Override
    public List<Role> showRoles() {
        return userDao.showRoles();
    }*/
}