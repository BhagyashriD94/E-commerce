package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import com.lcwd.electronic.store.electronicstore.repository.ProductRepository;
import com.lcwd.electronic.store.electronicstore.service.serviceImpl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ModelMapper modelMapper;
    Product product;
    @BeforeEach
    public void init(){
        product=Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("iphone")
                .description("this phone havin good camera and 5G features")
                .price(70000.00)
                .quantity(2)
                .descountprice(10000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
    }
    @Test
    public void createProductTest(){
        String productId = product.getProductId();
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertEquals("iphone",product1.getTitle());
        Assertions.assertNotNull(product1);
    }


}
