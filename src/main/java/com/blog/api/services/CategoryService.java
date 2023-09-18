package com.blog.api.services;

import com.blog.api.payloads.CategoryDto;

import java.util.List;


public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    void deleteCategory(Integer categoryId, String adEmail);

    CategoryDto getCategory(Integer categoryId);

    List<CategoryDto> getCategories();
}
