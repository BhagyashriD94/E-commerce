package com.lcwd.electronic.store.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.ProductDto;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.entity.Product;
import com.lcwd.electronic.store.electronicstore.service.ProductService;
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

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @MockBean
    private ProductService productService;
    @Autowired
    private ProductController productController;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Product product;

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
    public void createProductTest() throws Exception {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }
    private String convertObjectToJsonString(Object product){
        try{
            return new ObjectMapper().writeValueAsString(product);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Test
    public void updateProductTest() throws Exception {
        String productId = product.getProductId();
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(),Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/product/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getProductByIdTest() throws Exception {
        String productId = product.getProductId();
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.getProductById(Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getAllProductTest() throws Exception {
        ProductDto dto1 = ProductDto.builder().title("sumsung").description("this is latest version of sumsung").price(75000.00).descountprice(15000.00).quantity(1).live(true).stock(true).productImage("sum.png").build();
        ProductDto dto2 = ProductDto.builder().title("Nokia.4").description("this is latest version of sumsung").price(75000.00).descountprice(15000.00).quantity(1).live(true).stock(true).productImage("sum.png").build();
        ProductDto dto3 = ProductDto.builder().title("RedmiNote5").description("this is latest version of sumsung").price(75000.00).descountprice(15000.00).quantity(1).live(true).stock(true).productImage("sum.png").build();
        ProductDto dto4 = ProductDto.builder().title("RedmiNote6pro").description("this is latest version of sumsung").price(75000.00).descountprice(15000.00).quantity(1).live(true).stock(true).productImage("sum.png").build();
        PageableResponse<ProductDto> pageableResponse= new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(dto1,dto2,dto3,dto4));
        pageableResponse.setLastpage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000l);
        Mockito.when(productService.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

}
