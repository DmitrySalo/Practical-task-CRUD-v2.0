package com.example.webappcrud.service;

import com.example.webappcrud.dao.UserDao;
import com.example.webappcrud.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImp implements UserDetailsService {
    /*private final UserDao userDao;

    public UserDetailsServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }*/

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //return userDao.showByLogin(login);
        return userRepository.findUserByLogin(login);
    }
}