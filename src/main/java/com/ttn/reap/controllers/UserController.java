package com.ttn.reap.controllers;

import com.ttn.reap.entities.User;
import com.ttn.reap.exceptions.UserNotFoundException;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("/users")
    List<User> getUserList() {
        return userService.getUserList();
    }
    
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else throw new UserNotFoundException("No user with id " + id);
    }
    
    @PostMapping("/users")
    ResponseEntity<User> createNewUser(@RequestBody @Valid User user) {
        userService.save(user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/users/{id}")
    User editUser(@PathVariable Integer id, @RequestBody @Valid User user) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent())
            return userService.save(user);
        else throw new UserNotFoundException("No user with id " + user);
    }
}
