package com.example.easynotes.repository;

/**
 * Created by admin on 5/27/18.
 */

import com.example.easynotes.model.User;
import io.netty.util.concurrent.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Fredrick Oluoch
 *         http://www.blaqueyard.com
 *         0720106420 | 0722508906
 *         email: menty44@gmail.com
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    String findUserByEmail(String email);

    User findByMobile(String mobile);

    User findByEncry(UUID encry);

//    Page<User> findByRoleId(Long roleId, Pageable pageable);

//    @Modifying
    //@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
//    @Query(value = "UPDATE users SET role_id=2 WHERE email= ?1")
//    User setEncry(UUID encry);

//    List<User> findByEmailAndPassword(String email, String password);
    User findByEmailAndPassword(String email, String password);

    // Enables the distinct flag for the query
    List<User> findDistinctUserByEmailOrPassword(String email, String password);
    List<User> findUserDistinctByEmailOrPassword(String email, String password);

    // Enabling ignoring case for an individual property
    List<User> findByEmailIgnoreCase(String email);

    // Enabling ignoring case for all suitable properties
    List<User> findByEmailAndPasswordAllIgnoreCase(String email, String password);

    // Enabling static ORDER BY for a query
    List<User> findByEmailOrderByFirstnameAsc(String email);
    List<User> findByEmailOrderByFirstnameDesc(String email);

//    @Query("select u from User u")
//    Stream<User> findAllByCustomQueryAndStream();

    Stream<User> readAllByFirstnameNotNull();

//    @Query("select u from User u")
//    Stream<User> streamAllPaged(Pageable pageable);

    @Async
    Future<User> findByFirstname(String firstname);

//    @Async
//    Future<User> findByEmail(String email);

}
