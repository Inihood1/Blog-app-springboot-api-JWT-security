package com.blog.api.services.impl;

import com.blog.api.entities.Category;
import com.blog.api.entities.Role;
import com.blog.api.entities.User;
import com.blog.api.exceptions.AuthException;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category map = modelMapper.map(categoryDto, Category.class);
        Category saveCat = categoryRepo.save(map);
        return modelMapper.map(saveCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Cat Id", categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updated = categoryRepo.save(cat);
        return modelMapper.map(updated, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId, String adEmail) {
        User userAdmin = userRepo.findByEmail(adEmail).orElseThrow(() -> new ResourceNotFoundException("User", " Id " + adEmail, 0));
        if (userAdmin.getRole() == Role.ADMIN){
            Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Cat Id", categoryId));
            categoryRepo.delete(cat);
        }else {
            throw new AuthException("You don't have permission to delete a category!");
        }
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Cat Id", categoryId));
        return modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> cats = categoryRepo.findAll();
        return cats.stream().map((cat) -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }
}
