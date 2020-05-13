package com.hotmail.dolzhik.zalup_ca.repositories;

import com.hotmail.dolzhik.zalup_ca.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    Post findPostByUser_Login(String login);
    Post findPostByUser_Id(Integer id);
    List<Post> findPostsByUserLogin(String login);
}
