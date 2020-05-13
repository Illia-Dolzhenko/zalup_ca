package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByLogin(String login);
    @Modifying
    @Query(value = "update User u set u.points = u.points + ?2 where u.id = ?1")
    void changeScore(int id, int amount);
}
