package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto product = this.productService.createProduct(productDto);
        return  new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId){
        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        ProductDto productById = this.productService.getProductById(productId);
        return new ResponseEntity<>(productById,HttpStatus.OK);
    }
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        PageableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId){
        this.productService.deleteProduct(productId);
        ApiResponse apiResponse = ApiResponse.builder().message("product deleted sucessfully").success(true).status(HttpStatus.OK).build();
   return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }




}
