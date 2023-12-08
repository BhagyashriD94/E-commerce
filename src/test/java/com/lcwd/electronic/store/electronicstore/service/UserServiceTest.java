package com.lcwd.electronic.store.electronicstore.service;

import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.entity.User;
import com.lcwd.electronic.store.electronicstore.repository.UserRepository;
import com.lcwd.electronic.store.electronicstore.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    @InjectMocks
    private UserServiceImpl userService;
    @Autowired
    private ModelMapper modelMapper;
    User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .userId(UUID.randomUUID().toString())
                .name("Prachi Parihar")
                .email("prachi@gamil.com")
                .password("pr234")
                .gender("female")
                .imagename("abc.png")
                .about("I am java devloper and worked as a full stack devloper")
                .build();
    }

    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Prachi Parihar", user1.getName());
    }

    @Test
    public void updateUserTest() {
        String userId = user.getUserId();
        UserDto userDto = UserDto.builder()
                .name("Prachi p")
                .password("pr234")
                .gender("female")
                .imagename("abc.png")
                .about("this is updated detail for test")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateUser = userService.updateUser(userDto, userId);
        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updateUser.getName());
    }

    @Test
    public void deleteUserTest() {
        String userId = "userIdabc";
        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void getAllUserTest() {

        User user1 = User.builder()
                .name("shree")
                .email("shree@gamil.com")
                .password("sh234")
                .gender("female")
                .imagename("shr.png")
                .about("I am full stack devloper")
                .build();
        User user2 = User.builder()
                .name("Rani")
                .email("Rani@gamil.com")
                .password("rn234")
                .gender("female")
                .imagename("ran.png")
                .about("I am java devloper and worked as a back end devloper")
                .build();
        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3, allUser.getContent().size());
    }

    @Test
    public void getUserByIdTest() {
        String userId = user.getUserId();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUserById(userId);
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user.getName(), user1.getName(), "name not matched");
    }

    @Test
    public void getUserByEmail() {
        String emailId = user.getEmail();
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));
        UserDto userByEmail = userService.getUserByEmail(emailId);
        System.out.println(userByEmail.getEmail());
        Assertions.assertNotNull(userByEmail);
        Assertions.assertEquals(emailId, userByEmail.getEmail());
    }

    @Test
    public void searchUserTest() {
        User user1 = User.builder()
                .name("Anvit M")
                .email("Anvit@gamil.com")
                .password("pr234")
                .gender("male")
                .imagename("abc.png")
                .about("I am java devloper and worked as a full stack devloper")
                .build();
        User user2 = User.builder()
                .name("Kavya Parihar")
                .email("kavya@gamil.com")
                .password("pr234")
                .gender("female")
                .imagename("abc.png")
                .about("I am java devloper and worked as a full stack devloper")
                .build();
        String keywords = "female";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user, user1, user2));
        List<UserDto> userDtos = userService.searchUser(keywords);
        Assertions.assertNotNull(userDtos);
        Assertions.assertEquals(3, userDtos.size(), "size not matched");
    }



}
