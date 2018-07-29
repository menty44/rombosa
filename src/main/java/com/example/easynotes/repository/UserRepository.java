package com.example.easynotes.repository;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//import com.sun.tools.javac.util.List;

//import com.sun.tools.javac.util.List;

/**
 * @author Fredrick Oluoch
 *         http://www.blaqueyard.com
 *         0720106420 | 0722508906
 *         email: menty44@gmail.com
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
