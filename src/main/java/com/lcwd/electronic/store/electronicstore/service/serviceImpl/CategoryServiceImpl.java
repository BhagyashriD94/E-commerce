package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String cateId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(cateId);
        Category category = this.modelMapper.map(categoryDto, Category.class);
        this.categoryRepository.save(category);
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        return null;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory() {
        return null;
    }


    @Override
    public void deleteCategory(String categoryId) {

    }
}
