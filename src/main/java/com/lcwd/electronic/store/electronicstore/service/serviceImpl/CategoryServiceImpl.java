package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.helper.Helper;
import com.lcwd.electronic.store.electronicstore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
       category.setTitle(categoryDto.getTitle());
       category.setDescription(categoryDto.getDescription());
       category.setCoverImage(categoryDto.getCoverImage());
        Category category1 = this.categoryRepository.save(category);
        return this.modelMapper.map(category1,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
       Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = this.categoryRepository.findAll(p);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }


    @Override
    public void deleteCategory(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        this.categoryRepository.delete(category);

    }
}
