package com.blog.api.controllers;

import com.blog.api.confiq.AppConstants;
import com.blog.api.entities.Post;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.payloads.UploadImageResponse;
import com.blog.api.services.FileService;
import com.blog.api.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> posts = postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/posts/get")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "direction", defaultValue = AppConstants.DIRECTION, required = false) String direction
    ){
        PostResponse posts = postService.getAllPost(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/posts/get/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto posts = postService.getPostById(postId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/posts/del/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully", true);
    }


    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,  @PathVariable Integer postId){
        PostDto updated = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @GetMapping("/posts/search/{query}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String query){
        List<PostDto> posts = postService.searchPost(query);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /*
    *  // TODO: A post should not be uploaded before image, images should be uploaded before post
    *
    *   TODO: create a separate endpoint for image upload
     */
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
            throws IOException {
        //System.out.println("project image directory path " + path);
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);// cloud image directory and image file
        postDto.setImageName(fileName);
        PostDto savePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(savePost, HttpStatus.OK);
    }


    // Method created by Ini Hood
    @PostMapping("/post/upload")
    public ResponseEntity<UploadImageResponse> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String fileName = fileService.uploadImage(path, image); // cloud image directory and image file
        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setMessage("Image uploaded successfully");
        uploadImageResponse.setSuccess(true);
        uploadImageResponse.setFileName(fileName);
        uploadImageResponse.setFilePath(path+fileName);
        return new ResponseEntity<>(uploadImageResponse, HttpStatus.OK);
    }


    // View files

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException{
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}












