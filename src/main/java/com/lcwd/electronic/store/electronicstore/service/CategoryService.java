package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    CategoryDto getCategoryById(String categoryId);

    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteCategory(String categoryId);


}
