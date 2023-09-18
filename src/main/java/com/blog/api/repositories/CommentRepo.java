package com.blog.api.repositories;

import com.blog.api.entities.Comment;
import com.blog.api.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost(Post post);
}
