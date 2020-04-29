package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.PostRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.RatingRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RatingRepository ratingRepository;


    @GetMapping("/admin/removePost/{id}")
    public String test(@PathVariable(name = "id") Integer postId){

        Post post = postRepository.findById(postId).orElse(null);

        if(post!=null){
            postRepository.delete(post);
        }

        return "done";
    }
}
