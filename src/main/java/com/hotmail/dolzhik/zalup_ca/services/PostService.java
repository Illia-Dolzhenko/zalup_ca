package com.hotmail.dolzhik.zalup_ca.services;

import com.hotmail.dolzhik.zalup_ca.entities.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    void deletePost(Post post);
    Post findPostById(Integer id);
    Post findPostByUserId(Integer id);
    Post findPostByUserLogin(String login);
    List<Post> findAllPosts();
}
