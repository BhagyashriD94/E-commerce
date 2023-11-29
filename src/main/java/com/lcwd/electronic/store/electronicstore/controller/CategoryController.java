package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
     return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED) ;
    }
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ) {
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        this.categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message("category deleted sucessfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }






}
