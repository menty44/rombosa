package com.example.easynotes.repository;


/**
 * Created by admin on 8/7/18.
 */

import com.example.easynotes.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
