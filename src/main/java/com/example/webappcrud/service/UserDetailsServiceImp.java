package com.example.webappcrud.service;

import com.example.webappcrud.dao.UserRepository;
import com.example.webappcrud.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        Optional<User> showUser = Optional.ofNullable(userRepository.findUserByLogin(login));

        if (showUser.isPresent()) {
            user = showUser.get();
        }
        return user;
    }
}