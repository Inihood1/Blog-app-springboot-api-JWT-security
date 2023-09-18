package com.blog.api.services.impl;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "user id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);

        return modelMapper.map(savedPost, PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "post id", postId));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
        Sort sort = (direction.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        //TODO: use this response pattern for other endpoints
        // this is the correct structure of an API response
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));

        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements()); // total post in the DB
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;


    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() ->new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = postRepo.findByUser(user);
        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        return posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
