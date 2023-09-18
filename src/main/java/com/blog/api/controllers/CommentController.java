package com.blog.api.controllers;

import com.blog.api.entities.Comment;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CommentDto;
import com.blog.api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{postId}/{userId}")
    public ResponseEntity<CommentDto> addComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId
    ){
        CommentDto comment = commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    @GetMapping("/get/{postId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted!", true), HttpStatus.OK);
    }
}














