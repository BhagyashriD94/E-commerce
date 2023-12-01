package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    /**
     * @param categoryDto
     * @return
     * @auther Bhagyashri
     * @apiNote to save Category data into database
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Entering the request to save category data");
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        logger.info("completed the request by saving category data");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @auther Bhagyashri
     * @apiNote to update Category data in the database
     */
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Entering the request for updating category data with categoryId:{}", categoryId);
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);
        logger.info("completed the request for updating category data with categoryId:{}", categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived single Cateogry data from database
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        logger.info("Entering the request for retrived data with categoryId:{}", categoryId);
        CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
        logger.info("Completed the request for retrived data with categoryId:{}", categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived all Categories data from database
     */
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ) {
        logger.info("Entering the request for retrived all category data");
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for retrived all category data");
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @auther Bhagyashri
     * @apiNote to deleted category data at given categoryId from database
     */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Entering the request for deleted data with categoryId:{}", categoryId);
        this.categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message("category deleted sucessfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request for deleted data with categoryId:{}", categoryId);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }


}
