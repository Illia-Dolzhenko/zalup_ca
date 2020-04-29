package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.repositories.PostRepository;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        post.setCreationDate(new Timestamp(new Date().getTime()));
        post = postRepository.save(post);
        return post;
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post findPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Post findPostByUserId(Integer id) {
        return postRepository.findPostByUser_Id(id);
    }

    @Override
    public Post findPostByUserLogin(String login) {
        return postRepository.findPostByUser_Login(login);
    }

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

}
