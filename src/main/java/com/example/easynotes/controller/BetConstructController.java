package com.example.easynotes.controller;


/**
 * Created by admin on 9/15/18.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
@RequestMapping("/sms")
public class BetConstructController {

    @GetMapping("/sessid")
    public String getAllGenders() {
        return "hello world";
    }
}
