package com.bt_akademi.user_management.model.service;

import com.bt_akademi.user_management.model.entity.User;
import com.bt_akademi.user_management.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public abstract class AbstractUserService implements EntityService<User,Integer> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserRepository userRepository;

    protected abstract User findByUsername(String username);
}
