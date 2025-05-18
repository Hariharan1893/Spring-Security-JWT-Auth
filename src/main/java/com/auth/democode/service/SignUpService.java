package com.auth.democode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.democode.model.User;
import com.auth.democode.repository.UserRepo;

@Service
public class SignUpService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User signUp(String username, String password, String role) {

        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username " + username + " already taken");
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepo.save(user);
    }
}
