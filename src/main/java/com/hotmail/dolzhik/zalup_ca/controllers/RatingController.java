package com.hotmail.dolzhik.zalup_ca.controllers;

import com.hotmail.dolzhik.zalup_ca.dto.request.RatingDto;
import com.hotmail.dolzhik.zalup_ca.dto.ZalupcaResponse;
import com.hotmail.dolzhik.zalup_ca.entities.Post;
import com.hotmail.dolzhik.zalup_ca.entities.RateCategory;
import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.services.ICaptchaService;
import com.hotmail.dolzhik.zalup_ca.services.PostService;
import com.hotmail.dolzhik.zalup_ca.services.RatingService;
import com.hotmail.dolzhik.zalup_ca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RatingController {

    private final UserService userService;
    private final PostService postService;
    private final RatingService ratingService;
    private final ICaptchaService captchaService;


    @Autowired
    public RatingController(UserService userService, PostService postService, RatingService ratingService, ICaptchaService captchaService) {
        this.userService = userService;
        this.postService = postService;
        this.ratingService = ratingService;
        this.captchaService = captchaService;
    }

    @PostMapping(value = "/addRating")
    ResponseEntity addRating(@RequestBody @Valid RatingDto ratingDto, Principal principal) {
        if(captchaService.isValid(ratingDto.getToken(),"addRating")) {
            User user = userService.findByLogin(principal.getName());
            Post post = postService.findPostById(ratingDto.getPostId());
            if (post != null) {
                List<Rating> ratings = ratingService.getRatingsByUserAndPostId(user.getId(), post.getId());
                if (ratings.size() == 0 || !hasRatingInCategory(ratings, ratingDto.getCategory())) {
                    Rating rating = new Rating();
                    rating.setScore(ratingDto.getScore());
                    rating.setUser(user);
                    rating.setPost(post);
                    try {
                        rating.setCategory(RateCategory.valueOf(ratingDto.getCategory()));
                    } catch (IllegalArgumentException e) {
                        return new ResponseEntity<>(new ZalupcaResponse("Category does not exist."), HttpStatus.OK);
                    }
                    ratingService.addRating(rating);
                    return new ResponseEntity<>(new ZalupcaResponse("Rating added."), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ZalupcaResponse("Rating already exists."), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new ZalupcaResponse("Post does not exist."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ZalupcaResponse("Captcha is invalid."), HttpStatus.BAD_REQUEST);
    }

    private boolean hasRatingInCategory(List<Rating> ratings, String category){
        for(Rating rating : ratings){
            if (rating.getCategory().name().equals(category)){
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/categories")
    ResponseEntity getCategories(){
        List<String> categories;
        categories = Arrays.stream(RateCategory.values()).map(Enum::name).collect(Collectors.toList());
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }
}
