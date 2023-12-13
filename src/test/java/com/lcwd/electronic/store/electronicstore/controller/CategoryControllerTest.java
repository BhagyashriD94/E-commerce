package com.lcwd.electronic.store.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .title("mobile")
                .description("this category contain mobile related product")
                .coverImage("mob.png").build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Object category) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}

        @Test
        public void updateCategoryTest () throws Exception {
            String categoryId = category.getCategoryId();
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
            Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(categoryDto);
            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/category/" + categoryId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(convertObjectToJsonString(category))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").exists());
        }
    }
