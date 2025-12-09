package com.project.foundersfeild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.userrepo;
import com.project.foundersfeild.service.userserv;

import jakarta.validation.Valid;

@RestController
public class usercon {

    @Autowired
    userserv ser;

    @Autowired
    userrepo userrepo;

    // Get all users
    @GetMapping("/users")
    public List<user> getusers() {
        return ser.getusers();
    }

    // Get user by ID (public)
    @GetMapping("/api/users/{id}")
    public user getuserbyid(@PathVariable Long id) {
        return ser.getuserbyid(id);
    }

    // Search users by name
    @GetMapping("/users/search")
    public List<user> searchUsers(@RequestParam String name) {
        return ser.searchByName(name);
    }

    // Add new user
    @PostMapping("/users")
    public void adduser(@Valid @RequestBody user user) {
        ser.adduser(user);
    }

    // ⚠️ REMOVE this mapping:
    // @GetMapping("/{id}")  <-- causes MethodArgumentTypeMismatchException
}
