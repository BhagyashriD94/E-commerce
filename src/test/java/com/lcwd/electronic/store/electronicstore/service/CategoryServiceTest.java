package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicstore.service.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ModelMapper modelMapper;

    Category category;
    @BeforeEach
    public void init(){
        category=Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .title("mobile")
                .description( "this category contain mobile related product")
                .coverImage("mob.png").build();
    }
    @Test
    public void createCategoryTest(){
     String categoryId=category.getCategoryId();
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(modelMapper.map(category, CategoryDto.class));
        System.out.println(category1.getTitle());
        Assertions.assertEquals("mobile",category1.getTitle());
        Assertions.assertNotNull(category1);
    }
    @Test
    public void updateCategoryTest(){
       String categoryId=category.getCategoryId();
        CategoryDto categoryDto = CategoryDto.builder().title("SumsungNote5")
                .description("this is latest version of sumsung")
                .coverImage("sum.png").build();
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        System.out.println(categoryDto1.getTitle());
        Assertions.assertNotNull(categoryDto1);
        Assertions.assertEquals("SumsungNote5",categoryDto1.getTitle());
    }
    @Test
    public void getCategoryByIdTest(){
        String categoryId=category.getCategoryId();
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        System.out.println(categoryById);
        Assertions.assertEquals("mobile",categoryById.getTitle());

    }
//    public void getAllcate
//
}
