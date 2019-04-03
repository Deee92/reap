package com.ttn.reap.controllers;

import com.ttn.reap.entities.User;
import com.ttn.reap.exceptions.UserNotFoundException;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/ttn/ttn_dev/reap/src/main/resources/static/user-images/";
    
    @GetMapping("/users")
    List<User> getUserList() {
        return userService.getUserList();
    }
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else throw new UserNotFoundException("No user with id " + id);
    }
    
    @PostMapping("/users")
    public ModelAndView createNewUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, @RequestParam("image") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR ERROR ERROR");
            return new ModelAndView("index");
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                user.setPhoto(path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userService.save(user);
            System.out.println("User saved: " + user.toString());
            return new ModelAndView("dashboard");
        }
    }
    
    @PutMapping("/users/{id}")
    public User editUser(@PathVariable Integer id, @RequestBody @Valid User user) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent())
            return userService.save(user);
        else throw new UserNotFoundException("No user with id " + user);
    }
}
