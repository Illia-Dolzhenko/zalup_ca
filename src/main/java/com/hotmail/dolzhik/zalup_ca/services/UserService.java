package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.User;

import java.util.List;

public interface UserService {

    User register(User user);
    User findByLogin(String login);
    User findById(Integer id);
    List<User> findAll();
    void changePoints(User user, int amount);
}
