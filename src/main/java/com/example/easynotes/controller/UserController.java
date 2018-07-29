package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @GetMapping("/useremail/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String Email) {
        return userRepository.findByEmail(Email);
                //.orElseThrow(() -> new ResourceNotFoundException("User", "email", Email));
    }



    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                               @Valid @RequestBody User UserName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setEmail(UserName.getEmail());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}
