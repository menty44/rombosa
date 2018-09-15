package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.MyErrorRepository;
import com.example.easynotes.repository.RoleRepository;
import com.example.easynotes.repository.UserRepository;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
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
    RoleRepository roleRepository;

    @Autowired
    MyErrorRepository myErrorRepository;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UUID encry = UUID.randomUUID();

    @GetMapping("/users")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/userpost")
    //public User createUser(@Valid @RequestBody User user) {
    public HashMap<String ,String> createUser(@Valid @RequestBody User user ,HttpServletRequest request) {

        String value1 = request.getParameter("email");

        HashMap<String ,String> hashMap=new HashMap<>();

        hashMap.put("test", value1);

        return hashMap;
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }


    @GetMapping("/validateuseremail/{email}")
//    public User getUserByEmails(@PathVariable(value = "email") String Email) {
    public ResponseEntity<Map<String,String>> getvalidateEmail(@PathVariable(value = "email") String Email) throws ParseException {
        Map<String,String> response = new HashMap<String, String>();
        User lst = userRepository.findByEmail(Email);

        if(lst == null ){
            response.put("mg", "success");
            response.put("code", "00");
            response.put("desc", "email record is not in the database proceed to create a new user");
            return ResponseEntity.ok().body(response);
        }else {

            System.out.println("");
            System.out.println(lst.getEmail());

            response.put("msg", "email already been used, please try to register with a new one");
            response.put("code", "03");
            response.put("desc", lst.getEmail());

            return ResponseEntity.accepted().body(response);

        }
        //.orElseThrow(() -> new ResourceNotFoundException("User", "email", Email));
    }

    @GetMapping("/validatemobile/{mobile}")
//    public User getUserByEmails(@PathVariable(value = "email") String Email) {
    public ResponseEntity<Map<String,String>> getvalidateMobilel(@PathVariable(value = "mobile") String mobile) throws ParseException {
        Map<String,String> response = new HashMap<String, String>();
        User mob = userRepository.findByMobile(mobile);

        if(mob == null ){
            response.put("mg", "success");
            response.put("code", "00");
            response.put("desc", "mobile phone number record is not in the database proceed to create a new user");
            return ResponseEntity.ok().body(response);
        }else {

            System.out.println("");
            System.out.println(mob.getEmail());

            response.put("msg", "mobile phone number has already been used, please try to register with new number");
            response.put("code", "03");
            response.put("desc", mob.getMobile());

            return ResponseEntity.accepted().body(response);

        }
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
    public ResponseEntity<Map<String,String>> registerNewUser(
            @RequestParam(value = "firstname") String firstname,
                          @RequestParam(value = "lastname") String lastname,
                          @RequestParam(value = "mobile") String mobile,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String password) throws IOException {

        Map<String,String> response = new HashMap<String, String>();

            User myemail = userRepository.findByEmail(email);
            User mymobile = userRepository.findByMobile(mobile);

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
                user.setMyrole(2);
                //user.setRole(1);

                userRepository.save(user);

                sendMail(email, secu);

                response.put("ok", "save success");
                response.put("code", "00");
                return ResponseEntity.accepted().body(response);

        }else {
            String ts = "one of the parameters is missing";
            response.put("error", ts);
            response.put("code", "05");
            return ResponseEntity.badRequest().body(response);
        }
    }

//    public void setDefaultRole( UUID sec) {
//
//        //userRepository.findByEncry(sec);
//
//        User us = userRepository.findByEncry(sec);
//                //.orElseThrow(() -> new ResourceNotFoundException("User", "id", sec));
//
////        us.setName(roleName.getName());
//        us.setRole();
//
//        User updatedrole = roleRepository.save(us);
//        return updatedrole;
//
//        userRepository.setEncry(sec);
//
//    }

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
    public ResponseEntity<Map<String,String>> validateNewUser(@RequestParam(value = "auth", defaultValue = "not available") UUID auth)throws IOException{

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
            return ResponseEntity.accepted().body(response);
        }

    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> authenticateduser() {

        Map<String,String> response = new HashMap<String, String>();

        response.put("ok", "activation success");
        response.put("code", "00");

        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> faileduser() {

        Map<String,String> response = new HashMap<String, String>();

        response.put("error", " not validated");
        response.put("code", "03");

        return ResponseEntity.accepted().body(response);
    }

    @CrossOrigin
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String,String>> login(@RequestParam(value = "") String email,
                                                     @RequestParam(value = "") String password) throws IOException {

        System.out.println("my email" +  email);
        System.out.println("my password" + password);

        Map<String,String> response = new HashMap<String, String>();

        if(email!= null && !email.isEmpty() && password!= null && !password.isEmpty()){

            User usr = userRepository.findByEmailAndPassword(email, password);

            usr.getEmail();
            usr.getPassword();

            System.out.println("email from db"+usr.getEmail());
            System.out.println("pass from db"+usr.getPassword());

            if(usr.getEmail().equals(email) && usr.getPassword().equals(password)){
                response.put("ok", "00");
                response.put("msg","success");
                return ResponseEntity.accepted().body(response);
            }else {
                String ts = "paramsmissing";
                response.put("error", ts+" has an empty or a null value");
                response.put("code", "03");
                return ResponseEntity.badRequest().body(response);
            }
        }else {
            String ts = "paramsmissing";
            response.put("error", ts+" has an empty or a null value");
            response.put("code", "03");
            return ResponseEntity.badRequest().body(response);
        }

    }



    @CrossOrigin
    @RequestMapping(value = "acivatelogin", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public User acivatelogin(@RequestParam(value = "") String email,
                                                    @RequestParam(value = "") String password) throws IOException {

        System.out.println("my email" +  email);
        System.out.println("my password" + password);

        Map<String,String> response = new HashMap<String, String>();

        //User usr = userRepository.findByEmailAndPassword(email, password);

        return userRepository.findByEmailAndPassword(email, password);

    }


//    @GetMapping("/role/{roleId}/users")
//    public Page<User> getAllUserssByRoleId(@PathVariable (value = "roleId") Long roleId,
//                                                Pageable pageable) {
//        return userRepository.findByRoleId(roleId, pageable);
//    }



    @GetMapping("/role/{roleId}/users/{email}")
    public User getUserByEmail(@PathVariable (value = "roleId") Long roleId,
                                 @PathVariable (value = "email") String email
                               //@Valid @RequestBody User userRequest
    ) {
//        if(!roleId.existsById(roleId)) {
        if(!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("RoleId " + roleId + " not found");
        }

        return userRepository.findByEmail(email);
        //.orElseThrow(() -> new ResourceNotFoundException("Email " + email + "not found"));
    }


}
