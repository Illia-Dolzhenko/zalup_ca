package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.repositories.RatingRepository;
import com.hotmail.dolzhik.zalup_ca.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingByUserAndPostId(Integer userId, Integer postId) {
        return ratingRepository.findRatingByUserIdAndPostId(userId,postId);
    }
}
