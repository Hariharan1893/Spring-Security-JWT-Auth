package com.auth.democode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.democode.model.User;
import com.auth.democode.service.SignUpService;

@RestController
@RequestMapping("/")
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PostMapping("signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User newUser = signUpService.signUp(user.getUsername(), user.getPassword(), user.getRole());

        return ResponseEntity.ok(newUser);
    }
}
