package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.ImageResponce;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
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
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileservice;
    @Value("${product.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * @param productDto
     * @return
     * @auther Bhagyashri
     * @apiNote to save product data into database
     */
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering the request to save product data");
        ProductDto product = this.productService.createProduct(productDto);
        logger.info("Completed the request to save product data");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @auther Bhagyashri
     * @apiNote to update product data by productId from database
     */

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        logger.info("Entering the request for updating product data with productId:{}", productId);
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        logger.info("completed the request for updating product data with productId:{}", productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @auther Bhagyashri
     * @apinote to retrived single product data by productId from database
     */

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        logger.info("Entering the request for retrived data with productId:{}", productId);
        ProductDto productById = this.productService.getProductById(productId);
        logger.info("Completed the request for retrived data with productId:{}", productId);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived all product data from database
     */

    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the request for retrived all product data");
        PageableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed the request for retrived all product data");
        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @auther Bhagyashri
     * @apiNote to delete product data from database by productId
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        logger.info("Entering the request for deleted data with productId:{}", productId);
        this.productService.deleteProduct(productId);
        ApiResponse apiResponse = ApiResponse.builder().message("product deleted sucessfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request for deleted data with productId:{}", productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived product data of all live product in database
     */
    @GetMapping("/product/allLive")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the request for retrived all live data");
        PageableResponse<ProductDto> allLive = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for retrived all live data");
        return new ResponseEntity<>(allLive, HttpStatus.OK);
    }

    /**
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived product data by subTitle in database
     */
    @GetMapping("/product/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitlecontaining(
            @PathVariable String subTitle,
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Entering the request for retrived data by subTitle:{}", subTitle);
        PageableResponse<ProductDto> productDtoPageableResponse = this.productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for retrived data by subTitle:{}", subTitle);
        return new ResponseEntity<>(productDtoPageableResponse, HttpStatus.OK);
    }

    /**
     * @param productId
     * @param image
     * @return
     * @throws IOException
     * @auther Bhagyashri
     * @apiNote to upload product image by productId to database
     */
    @PostMapping("/Image/{productId}")
    public ResponseEntity<ImageResponce> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image) throws IOException {
        String filename = fileservice.uploadFile(image, imagePath);
        ProductDto productDto = productService.getProductById(productId);
        productDto.setProductImage(filename);
        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        ImageResponce responce = ImageResponce.builder().imageName(updateProduct.getProductImage()).message("Image uploaded sucessfully").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(responce, HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @param response
     * @throws IOException
     * @auther Bhagyashri
     * @apiNote to server product image by productId
     */
    @GetMapping("/image/{productId}")
    public void serverProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getProductById(productId);
        InputStream resource = fileservice.getResource(imagePath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
