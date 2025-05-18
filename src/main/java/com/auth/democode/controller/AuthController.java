package com.auth.democode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.democode.model.AuthRequest;
import com.auth.democode.utils.JWTUtil;

@RestController
public class AuthController {

    // use the Authentication Manager
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {

        try {

            // TO: Authenticate the user credentials with the AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // TO: Generate JWT Token using the JJWT Library

            return jwtUtil.generateToken(authRequest.getUsername());

        } catch (Exception e) {
            throw e;

        }
    }
}
