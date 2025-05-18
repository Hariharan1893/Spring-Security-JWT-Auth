package com.auth.democode.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

  @GetMapping
  public String greetings() {
    return "Welcome to the Home page....";
  }

  @GetMapping("afterauthall")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public String afterAuthAll() {
    return "Authenticated";
  }

  @GetMapping("user")
  @PreAuthorize("hasRole('USER')")
  public String afterAuthUser() {
    return "Authenticated User";
  }

  @GetMapping("admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String afterAuthAdmin() {
    return "Authenticated Admin";
  }
}
