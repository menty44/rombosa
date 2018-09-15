package com.example.easynotes.controller;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Role;
import com.example.easynotes.repository.RoleRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/role")
    public Page<Role> getAllRole(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @PostMapping("/role")
    public Role createRole(@Valid @RequestBody Role role) {

        return roleRepository.save(role);
    }

    @GetMapping("/role/{id}")
    public Role getroleById(@PathVariable(value = "id") Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("role", "id", roleId));
    }

    @PutMapping("/role/{id}")
    public Role updateRole(@PathVariable(value = "id") Long roleId,
                           @Valid @RequestBody Role roleName) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        role.setName(roleName.getName());

        Role updatedrole = roleRepository.save(role);
        return updatedrole;
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        roleRepository.delete(role);

        return ResponseEntity.ok().build();
    }

}
