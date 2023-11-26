package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicstore.dtos.UserDto;
import com.lcwd.electronic.store.electronicstore.entity.User;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.helper.Helper;
import com.lcwd.electronic.store.electronicstore.repository.UserRepository;
import com.lcwd.electronic.store.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        String strId = UUID.randomUUID().toString();
        userDto.setUserId(strId);
        logger.info("Initiated the dao call for saving user data");
        User user = this.modelMapper.map(userDto, User.class);
        User saveduser = this.userRepository.save(user);
        logger.info("completed the dao call for saving user data");
        return this.modelMapper.map(saveduser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiated the dao call for update user data by userId:{}", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImagename(userDto.getImagename());
        User user2 = this.userRepository.save(user);
        logger.info("completed the dao call for updating user data by userId:{}", userId);
        return this.modelMapper.map(user2, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiated the dao call for retriving user data by userId{}:", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("completed the dao call for retriving user data by userId{}:", userId);
        return this.modelMapper.map(user, UserDto.class);
    }


    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        logger.info("Initiated the dao call for retriving all user data");
        Page<User> page = this.userRepository.findAll(p);
        logger.info("completed the dao call for retriving all user data");
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        return pageableResponse;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiated the dao call for delete user data by userId{}:", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("completed the dao call for delete  user data by userId{}:", userId);
        this.userRepository.delete(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiated the dao call for retrive user data by email{}:", email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        logger.info("completed the dao call for retrive  user data by email{}:", email);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiated the dao call for retrive user data by keyword{}:", keyword);
        List<User> listedusers = this.userRepository.findByNameContaining(keyword);
        logger.info("completed the dao call for retrive  user data by keyword{}:", keyword);
        List<UserDto> collecteduserDtos = listedusers.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return collecteduserDtos;
    }
}
