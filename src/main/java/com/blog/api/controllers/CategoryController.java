package com.blog.api.controllers;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
        CategoryDto updatedCat = categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<>(updatedCat, HttpStatus.OK);
    }


    @DeleteMapping("del/{catId}/{email}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId, @PathVariable String email){
        categoryService.deleteCategory(catId, email);
        return new ResponseEntity<>(new ApiResponse("Category deleted", true), HttpStatus.OK);
    }

    @GetMapping("/get/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto catDto = categoryService.getCategory(catId);
        return new ResponseEntity<>(catDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> catList = categoryService.getCategories();
        return ResponseEntity.ok(catList);
    }
}
















