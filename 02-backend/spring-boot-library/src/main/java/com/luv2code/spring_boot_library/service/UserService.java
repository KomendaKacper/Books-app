package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.entity.User;

public interface UserService {

    User findByUsername(String username);
}
