package com.blog.api.payloads;

import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer id;
    private String content;
    private UserDto user;
}
