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

    /**
     * @auther Bhagyashri
     * @apiNote to save Category data into database
     * @param categoryDto
     * @return
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
     return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED) ;
    }

    /**
     * @auther Bhagyashri
     * @apiNote to update Category data in the database
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    /**
     * @auther Bhagyashri
     * @apiNote to retrived single Cateogry data from database
     * @param categoryId
     * @return
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    /**
     * @auther Bhagyashri
     * @apiNote to retrived all Categories data from database
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ) {
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    /**
     * @auther Bhagyashri
     * @apiNote to deleted category data at given categoryId from database
     * @param categoryId
     * @return
     */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        this.categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message("category deleted sucessfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }






}
