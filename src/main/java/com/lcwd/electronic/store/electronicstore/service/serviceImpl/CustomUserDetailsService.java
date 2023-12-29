package com.lcwd.electronic.store.electronicstore.service.serviceImpl;

import com.lcwd.electronic.store.electronicstore.constants.AppConstant;
import com.lcwd.electronic.store.electronicstore.entity.User;
import com.lcwd.electronic.store.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(AppConstant.Not_Found));
        return user;
    }
}
