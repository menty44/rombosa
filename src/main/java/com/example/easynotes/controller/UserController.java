package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.MyErrorRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    MyErrorRepository myErrorRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/userpost")
    //public User createUser(@Valid @RequestBody User user) {
    public HashMap<String ,String> createUser(@Valid @RequestBody User user ,HttpServletRequest request) {

        String value1 = request.getParameter("email");

//        User msg;
//        //msg = userRepository.save(user);
//        msg = userRepository.save(user);
//
        HashMap<String ,String> hashMap=new HashMap<>();
//        User i=userRepository.findByEmail(user.getEmail());
//        if(msg.getEmail()!= null){
//            hashMap.put("msg","success");
//            hashMap.put("code","00");
//            hashMap.put("email",msg.getEmail());
//            hashMap.put("firstname",msg.getFirstname());
//            hashMap.put("lastname",msg.getLastname());
//            hashMap.put("mobile",msg.getMobile());
//        }else {
//            hashMap.put("msg","Error");
//            hashMap.put("code","03");
//        }

        hashMap.put("test", value1);


        return hashMap;
    }

//    @PostMapping("/gender")
//    public Gender createGender(@Valid @RequestBody Gender gender) {
//        return genderRepository.save(gender);
//    }


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

    @CrossOrigin
    @RequestMapping(value = "checker", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String,String>> getRev(@RequestParam(value = "text") String text) throws IOException {

        Map<String,String> response = new HashMap<String, String>();

        boolean isValid = true;

        if(text!= null && !text.isEmpty()){
            response.put("ok", text);
            return ResponseEntity.accepted().body(response);
        }else {
            String ts = "text";
            response.put("error", ts+" has an empty or a null value");
            return ResponseEntity.badRequest().body(response);
        }

    }
}
