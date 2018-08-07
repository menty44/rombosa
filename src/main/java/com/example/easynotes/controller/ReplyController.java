//package com.example.easynotes.controller;
//
//import com.example.easynotes.exception.ResourceNotFoundException;
//import com.example.easynotes.model.Comment;
//import com.example.easynotes.model.Reply;
//import com.example.easynotes.repository.PostRepository;
//import com.example.easynotes.repository.ReplyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
//public class ReplyController {
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @GetMapping("/posts/{postId}/comments/replies")
//    public Page<Reply> getAllReplysByPostId(@PathVariable (value = "postId") Long postId,
//                                            Pageable pageable) {
//        return replyRepository.findByCommentId(postId, pageable);
//    }
//
//    @PostMapping("/posts/{postId}/comments/replies")
//    public Reply createReply(@PathVariable (value = "commentId") Long postId,
//                                 @Valid @RequestBody Reply reply) {
//        return postRepository.findById(postId).map(post -> {
//            comment.setPost(post);
//            return replyRepository.save(comment);
//        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
//    }
//
//    @PutMapping("/posts/{postId}/comments/{commentId}/replies/{repliesID}")
//    public Reply updateReply(@PathVariable (value = "postId") Long postId,
//                                 @PathVariable (value = "commentId") Long commentId,
//                                 @Valid @RequestBody Comment commentRequest) {
//        if(!postRepository.existsById(postId)) {
//            throw new ResourceNotFoundException("PostId " + postId + " not found");
//        }
//
//        return replyRepository.findById(commentId).map(comment -> {
//            comment.setText(commentRequest.getText());
//            return replyRepository.save(comment);
//        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
//    }
//
//    @DeleteMapping("/posts/{postId}/comments/{commentId}")
//    public ResponseEntity<?> deleteReply(@PathVariable (value = "postId") Long postId,
//                                           @PathVariable (value = "commentId") Long commentId) {
//        if(!postRepository.existsById(postId)) {
//            throw new ResourceNotFoundException("PostId " + postId + " not found");
//        }
//
//        return replyRepository.findById(commentId).map(comment -> {
//            replyRepository.delete(comment);
//            return ResponseEntity.ok().build();
//        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
//    }
//}
