package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.*;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.CategoryService;
import com.lcwd.electronic.store.electronicstore.service.FileService;
import com.lcwd.electronic.store.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @Value("${category.cover.image.path}")
    private String coverImageUploadpath;
    @Autowired
    private ProductService productService;
    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Value("${category.cover.image.path}")
    private String coverimageUploadPath;

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

    /**
     * @param image
     * @param categoryId
     * @return
     * @throws IOException
     * @auther Bhagyashri
     * @apiNote to upload cover image at given categoryId into database
     */
    @PostMapping("/coverimage/{categoryId}")
    public ResponseEntity<ImageResponce> uploadCoverImage(@RequestParam("coverImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        String uploadFile = fileService.uploadFile(image, coverimageUploadPath);
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        categoryById.setCoverImage(uploadFile);
        CategoryDto categoryDto = categoryService.updateCategory(categoryById, categoryId);
        ImageResponce imageResponce = ImageResponce.builder().imageName(uploadFile).message("Image uploaded sucessfully").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponce, HttpStatus.CREATED);
    }

    /**
     * @param categoryId
     * @param response
     * @throws IOException
     * @auther Bhagyashri
     * @apiNote to server coverimage in database by categoryId
     */
    @GetMapping("/coverimage/{categoryId}")
    public void servercoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryById = categoryService.getCategoryById(categoryId);
        InputStream resource = fileService.getResource(coverimageUploadPath, categoryById.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto, @PathVariable String categoryId) {
        ProductDto productwithCategory = this.productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productwithCategory, HttpStatus.CREATED);
    }
    @PutMapping("/category/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId,
                                                              @PathVariable String productId){
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }


}
