package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Gender;
import com.example.easynotes.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class GenderController {

    @Autowired
    GenderRepository genderRepository;


    @GetMapping("/gender")
    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    @PostMapping("/gender")
    public Gender createGender(@Valid @RequestBody Gender gender) {
        return genderRepository.save(gender);
    }

    @GetMapping("/gender/{id}")
    public Gender getGenderById(@PathVariable(value = "id") Long genderId) {
        return genderRepository.findById(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender", "id", genderId));
    }

    @PutMapping("/gender/{id}")
    public Gender updateGender(@PathVariable(value = "id") Long genderId,
                           @Valid @RequestBody Gender GenderName) {

        Gender gender = genderRepository.findById(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender", "id", genderId));

        //gender.setTitle(noteDetails.getTitle());
        gender.setName(GenderName.getName());

        Gender updatedGender = genderRepository.save(gender);
        return updatedGender;
    }

    @DeleteMapping("/gender/{id}")
    public ResponseEntity<?> deleteGender(@PathVariable(value = "id") Long genderId) {
        Gender gender = genderRepository.findById(genderId)
                .orElseThrow(() -> new ResourceNotFoundException("Gender", "id", genderId));

        genderRepository.delete(gender);

        return ResponseEntity.ok().build();
    }
}
