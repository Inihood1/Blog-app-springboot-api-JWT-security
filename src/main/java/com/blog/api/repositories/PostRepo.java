package com.blog.api.repositories;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user); // find posts by user

    List<Post> findByCategory(Category category); // find posts by category

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitleContaining(@Param("key") String word);

    List<Post> findByTitleContaining(String title);


}
















