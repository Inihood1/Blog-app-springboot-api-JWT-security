package com.blog.api.services;

import com.blog.api.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    List<CommentDto> getCommentsByPostId(Integer postId);

    void deleteComment(Integer commentId);
}
