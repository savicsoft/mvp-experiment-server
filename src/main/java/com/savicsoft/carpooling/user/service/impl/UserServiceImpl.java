package com.savicsoft.carpooling.user.service.impl;

import com.savicsoft.carpooling.user.repository.UserRepository;
import com.savicsoft.carpooling.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


}
