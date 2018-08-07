package com.example.easynotes.repository;


/**
 * Created by admin on 8/7/18.
 */

import com.example.easynotes.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */


@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByCommentId(Long postId, Pageable pageable);


}
