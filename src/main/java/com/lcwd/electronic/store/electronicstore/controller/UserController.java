package com.lcwd.electronic.store.electronicstore.controller;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.ImageResponce;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.helper.ApiResponse;
import com.lcwd.electronic.store.electronicstore.service.FileService;
import com.lcwd.electronic.store.electronicstore.service.UserService;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

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
     * @apiNote to retrived user data by userId from database
     */
    @GetMapping("/user/userId/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Entering the request for retrived data with userId:{}", userId);
        UserDto userById = this.userService.getUserById(userId);
        logger.info("completed the request for retrived data with userId:{}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @auther Bhagyashri
     * @apiNote to retrived all user data from database
     */

    @GetMapping("/users")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir

    ) {
        logger.info("Entering the request for retrived data for all user");
        PageableResponse<UserDto> allUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        logger.info("completed the request for retrived data for all user");
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @auther Bhagyashri
     * @apiNote to delete user data at given userId in database
     */

    @DeleteMapping("/user/userId/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        this.userService.deleteUser(userId);
        logger.info("Entering the request for deleting data with userId:{}", userId);
        ApiResponse message = ApiResponse.builder().message("User is deleted successfully").success(true).status(HttpStatus.OK).build();
        logger.info("completed the request for deleting data with userId:{}", userId);
        return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author Bhagyashri
     * @apiNote to retrived user data by email from database
     */

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Entering the request for retrived data by email");
        UserDto userByEmail = this.userService.getUserByEmail(email);
        logger.info("completed the request for retrived data by email");
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author Bhagyashri
     * @apiNote to search user data by keyword from database
     */

    @GetMapping("/user/keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Entering the request for retrived data by search keyword");
        List<UserDto> userDtos = this.userService.searchUser(keyword);
        logger.info("completed the request for retrived data by search keyword");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponce> uploadUserImage(@RequestParam("userImage") MultipartFile image,@PathVariable String userId) throws IOException {
        String imagename = fileService.uploadFile(image, imageUploadPath);
        UserDto userById = userService.getUserById(userId);
        userById.setImagename(imagename);
        UserDto userDto = userService.updateUser(userById, userId);
        ImageResponce imageResponce=ImageResponce.builder().imageName(imagename).message("Image uploaded sucessfully").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponce,HttpStatus.CREATED);
    }
    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

        UserDto user = userService.getUserById(userId);
        logger.info("User image name: {}",user.getImagename());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());


    }


}
