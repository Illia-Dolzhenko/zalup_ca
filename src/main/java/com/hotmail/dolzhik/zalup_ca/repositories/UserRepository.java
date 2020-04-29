package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByLogin(String login);
}
