package com.luv2code.spring_boot_library.service.impl;

import com.luv2code.spring_boot_library.dao.UserRepository;
import com.luv2code.spring_boot_library.entity.User;
import com.luv2code.spring_boot_library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
