package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.RatingDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.RateCategory;
import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import com.hotmail.dolzhik.zalup_ca.services.RatingService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RatingController {

    private final UserService userService;
    private final PostService postService;
    private final RatingService ratingService;


    @Autowired
    public RatingController(UserService userService, PostService postService, RatingService ratingService) {
        this.userService = userService;
        this.postService = postService;
        this.ratingService = ratingService;
    }

    @PostMapping(value = "/addRating")
    ResponseEntity addRating(@RequestBody @Valid RatingDto ratingDto, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Post post = postService.findPostById(ratingDto.getPostId());

        if (post != null) {
            Rating rating = ratingService.getRatingByUserAndPostId(user.getId(), post.getId());
            if (rating == null || !rating.getCategory().name().equals(ratingDto.getCategory())) {
                rating = new Rating();
                rating.setScore(ratingDto.getScore());
                rating.setUser(user);
                rating.setPost(post);
                try {
                    rating.setCategory(RateCategory.valueOf(ratingDto.getCategory()));
                }catch (IllegalArgumentException e){
                    return new ResponseEntity<>(new ZalupcaResponse("Category does not exist."), HttpStatus.OK);
                }
                ratingService.addRating(rating);
                return new ResponseEntity<>(new ZalupcaResponse("Rating added."), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ZalupcaResponse("Rating already exists."), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new ZalupcaResponse("Post does not exist."), HttpStatus.OK);
    }
}
