package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.entities.Role;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.RoleRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.UserRepository;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user.setPoints(100);

        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
