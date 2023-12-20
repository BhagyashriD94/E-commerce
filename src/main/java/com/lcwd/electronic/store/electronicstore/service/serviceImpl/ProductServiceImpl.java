package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.entity.Category;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.helper.Helper;
import com.lcwd.electronic.store.electronicstore.repository.CategoryRepository;
import com.lcwd.electronic.store.electronicstore.repository.ProductRepository;
import com.lcwd.electronic.store.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String stringId = UUID.randomUUID().toString();
        productDto.setProductId(stringId);
        logger.info("Initiating dao call to save product data");
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product product1 = this.productRepository.save(product);
        logger.info("Completed dao call to save product data");
        return this.modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        logger.info("Initiating dao call to update product data by productId:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAddedDate(productDto.getAddedDate());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());
        Product updatedproduct = this.productRepository.save(product);
        logger.info("Completed dao call to update product data by productId:{}", productId);
        return this.modelMapper.map(updatedproduct, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String productId) {
        logger.info("Initiating dao call to retrived product data by productId:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("Completed dao call to retrived product data by productId:{}", productId);
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        logger.info("Initiating dao call to retrived all product data");
        Page<Product> page = this.productRepository.findAll(p);
        logger.info("completed dao call to retrived all product data");
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public void deleteProduct(String productId) {
        logger.info("Initiating dao call to deleted product data by productId:{}", productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("completed dao call to deleted product data by productId:{}", productId);
        this.productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        logger.info("Initiating dao call to retrived all product live data");
        Page<Product> byLiveTrue = this.productRepository.findByLiveTrue(p);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byLiveTrue, ProductDto.class);
        logger.info("completed dao call to retrived all product live data");
        return pageableResponse;
    }
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> byTitleContaining = this.productRepository.findByTitleContaining(subTitle, p);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(byTitleContaining, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        String stringId = UUID.randomUUID().toString();
        productDto.setProductId(stringId);
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product product1 = this.productRepository.save(product);
        return this.modelMapper.map(product1, ProductDto.class);
    }
}
