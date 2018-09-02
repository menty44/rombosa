package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.MyErrorRepository;
import com.example.easynotes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    private JavaMailSender sender;

    @Autowired
    JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UUID encry = UUID.randomUUID();

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

    //new user reg
    @CrossOrigin
    @RequestMapping(value = "regnewuser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String,String>> registerNewUser(@RequestParam(value = "firstname", defaultValue = "not available") String firstname,
                      @RequestParam(value = "lastname", defaultValue = "not available") String lastname,
                      @RequestParam(value = "mobile", defaultValue = "not available") String mobile,
                      @RequestParam(value = "email", defaultValue = "not available") String email,
                      @RequestParam(value = "password", defaultValue = "not available") String password) throws IOException {

        Map<String,String> response = new HashMap<String, String>();

        if(firstname!= null && !firstname.isEmpty() && lastname!= null && !lastname.isEmpty() && mobile!= null && !mobile.isEmpty() && email!= null && !email.isEmpty() && password!= null && !password.isEmpty()){

            User user = new User();
            UUID secu = encry ;
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setPassword(password);
            user.setEncry(secu);
            user.setActivated("0");

            userRepository.save(user);

            sendMail(email, secu);

            response.put("ok", "save success");
            response.put("code", "00");
            return ResponseEntity.accepted().body(response);
            //return ResponseEntity.accepted().body(response);
        }else {
            String ts = "paramsmissing";
            response.put("error", ts+" has an empty or a null value");
            response.put("code", "03");
            return ResponseEntity.badRequest().body(response);
        }
    }

    public void sendMail( String to, UUID secu) {

        if (to != null && !to.isEmpty()) {
            SimpleMailMessage mail = new SimpleMailMessage();

            try {
                String subject = "AfroBoot Account Activation";
                String body = "Welcome to Afroboot\n"+"Please Click on this link "+"http://localhost:8080/api/validate?auth="+secu+" to activate your account";
                String from = "info@blaqueyard.com";

                mail.setFrom(from);
                mail.setTo(to);
                mail.setSubject(subject);
                mail.setText(body);

                System.out.println("Sending Email For Activation...");

                javaMailSender.send(mail);
                System.out.println("Done! Sending Emails");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else {
            System.out.println("Missing Parameters");
        }

    }

    //new user reg
    @CrossOrigin
    @RequestMapping(value = "validate", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String,String>>  validateNewUser(@RequestParam(value = "auth", defaultValue = "not available") UUID auth)throws IOException{

        Map<String,String> response = new HashMap<String, String>();

//        if (auth != null && !auth.isEmpty()) {
        if (auth != null) {

            User us = userRepository.findByEncry(auth);
                    //.orElseThrow(() -> new ResourceNotFoundException("Validation", "id", auth));

            us.setActivated("1");

            userRepository.save(us);

            response.put("ok", "activation success");
            response.put("code", "00");
            return ResponseEntity.accepted().body(response);

        }else {
            response.put("error", " not validated");
            response.put("code", "03");
            return ResponseEntity.badRequest().body(response);
        }

    }

}
