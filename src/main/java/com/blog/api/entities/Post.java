package com.blog.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "post_title", length = 100, nullable = false)
    private String title;
    @Column(name = "post_content", length = 10000, nullable = false)
    private String content;
    private String imageName;
    private Date addedDate;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();


}

















