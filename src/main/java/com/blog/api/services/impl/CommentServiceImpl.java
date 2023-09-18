package com.blog.api.services.impl;

import com.blog.api.entities.Comment;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CommentDto;
import com.blog.api.payloads.PostDto;
import com.blog.api.repositories.CommentRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "user id", userId));
        Post post = postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post); // this saves post in comment table
        comment.setUser(user);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "post id", postId));
        List<Comment> comments = commentRepo.findByPost(post);
        return comments.stream().map((com) -> modelMapper.map(com, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "comment id", commentId));
        commentRepo.delete(comment);
    }
}
