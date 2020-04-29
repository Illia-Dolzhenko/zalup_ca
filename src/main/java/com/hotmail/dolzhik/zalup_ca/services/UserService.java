package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.User;

public interface UserService {

    User register(User user);
    User findByLogin(String login);
    User findById(Integer id);
}
