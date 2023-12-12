package com.lcwd.electronic.store.electronicstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.entity.User;
import com.lcwd.electronic.store.electronicstore.service.UserService;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private User user;
    @BeforeEach
    public void init(){
        user= User.builder()
                .userId(UUID.randomUUID().toString())
                .name("Prachi Parihar")
                .email("prachi@gamil.com")
                .password("Pra@2346")
                .gender("female")
                .imagename("abc.png")
                .about("I am java devloper and worked as a full stack devloper")
                .build();
    }
    @Test
    public void createUserTest() throws Exception {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/apis/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
         }
    private String convertObjectToJsonString(Object user){
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
//    @Test
//    public void updateUser() throws Exception {
//        String userId= user.getUserId();
//        UserDto userDto = modelMapper.map(user, UserDto.class);
//        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
//        this.mockMvc.perform(MockMvcRequestBuilders.put("/apis/user/userId/"+userId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJsonString(user))
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").exists());
//    }
//    @Test
//    public void getUserById() throws Exception {
//        String userId= user.getUserId();
//        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(userDto);
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/apis/user/userId/"+userId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJsonString(user))
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").exists());
//    }
}
