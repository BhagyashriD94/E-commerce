package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.CategoryDto;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.entity.Category;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public void init() {
        product = Product.builder()
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
    public void createProductTest() {
        String productId = product.getProductId();
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.createProduct(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertEquals("iphone", product1.getTitle());
        Assertions.assertNotNull(product1);
    }

    @Test
    public void updateproductTest() {
        String productId = product.getProductId();
        ProductDto productDto = ProductDto.builder()
                .productId(UUID.randomUUID().toString())
                .title("RedmiNote6")
                .description("this phone havin good camera and 5G features")
                .price(60000.00)
                .quantity(2)
                .descountprice(15000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        System.out.println(updateProduct.getTitle());
        Assertions.assertNotNull(updateProduct);
        Assertions.assertEquals("RedmiNote6", updateProduct.getTitle());
    }

    @Test
    public void getProductByIdTest() {
        String productId = product.getProductId();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        ProductDto productDto = productService.getProductById(productId);
        System.out.println(productDto);
        Assertions.assertEquals("iphone", productDto.getTitle());
    }

    @Test
    public void getAllProductest() {
        Product product1 = Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("Motrolo")
                .description("this phone havin good camera and 5G features")
                .price(70000.00)
                .quantity(2)
                .descountprice(10000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
        Product product2 = Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("Nokia 7")
                .description("this phone havin good camera and 5G features")
                .price(70000.00)
                .quantity(2)
                .descountprice(10000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
        List<Product> list = Arrays.asList(product, product1, product2);
        Page<Product> page = new PageImpl<>(list);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(1, 2, "title", "asc");
        int size = allProduct.getContent().size();
        Assertions.assertEquals(3, size);
    }
    @Test
    public void deleteproductTest() {
        String productId = "fhgdk";
        Mockito.when(productRepository.findById("fhgdk")).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
    @Test
    public void getAllLiveproductTest(){
        Product product1 = Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("Motrolo")
                .description("this phone havin good camera and 5G features")
                .price(70000.00)
                .quantity(2)
                .descountprice(10000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
        Product product2 = Product.builder()
                .productId(UUID.randomUUID().toString())
                .title("Nokia 7")
                .description("this phone havin good camera and 5G features")
                .price(70000.00)
                .quantity(2)
                .descountprice(10000.00)
                .stock(true)
                .live(true)
                .productImage("ipn.png").build();
        List<Product> productlist=Arrays.asList(product,product1,product2);
        Page<Product> page = new PageImpl<>(productlist);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allLive = productService.getAllLive(1,2,"title","desc");
        Assertions.assertEquals(3,allLive.getContent().size());
    }
}
