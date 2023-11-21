package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.UserService;
import com.lcwd.electronic.store.electronicstore.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * @param userDto
     * @return
     * @author Bhagyashri
     * @apiNote To save userdata into database
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Entering the request for save user");
        UserDto userDto1 = this.userService.createUser(userDto);
        logger.info("completed the request for saving user data");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @author Bhagyashri
     * @apiNote to update user data from database
     */
    @PutMapping("/user/userId/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        logger.info("Entering the request for update data with userId:{}", userId);
        UserDto updateuserDto = this.userService.updateUser(userDto, userId);
        logger.info("completed the request for update data with userId:{}", userId);
        return new ResponseEntity<>(updateuserDto, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Bhagyashri
     * @apiNote to
     */
    @GetMapping("user/userId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Entering the request for retrived data with userId:{}", userId);
        UserDto userById = this.userService.getUserById(userId);
        logger.info("completed the request for retrived data with userId:{}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {
        logger.info("Entering the request for retrived data for all user");
        PageableResponse<UserDto> allUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for retrived data for all user");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @DeleteMapping("user/userId/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        this.userService.deleteUser(userId);
        logger.info("Entering the request for deleting data with userId:{}", userId);
        ApiResponse message = ApiResponse.builder().message("User is deleted successfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request for deleting data with userId:{}", userId);
        return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);
    }

    @GetMapping("user/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Entering the request for retrived data by email");
        UserDto userByEmail = this.userService.getUserByEmail(email);
        logger.info("completed the request for retrived data by email");
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    @GetMapping("user/keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Entering the request for retrived data by search keyword");
        List<UserDto> userDtos = this.userService.searchUser(keyword);
        logger.info("completed the request for retrived data by search keyword");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


}
