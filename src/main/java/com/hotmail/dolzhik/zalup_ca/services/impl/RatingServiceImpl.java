package com.hotmail.dolzhik.zalup_ca.services.impl;

import com.hotmail.dolzhik.zalup_ca.entities.Rating;
import com.hotmail.dolzhik.zalup_ca.entities.User;
import com.hotmail.dolzhik.zalup_ca.repositories.RatingRepository;
import com.hotmail.dolzhik.zalup_ca.repositories.UserRepository;
import com.hotmail.dolzhik.zalup_ca.services.RatingService;
import com.hotmail.dolzhik.zalup_ca.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void addRating(Rating rating) {
        ratingRepository.save(rating);
        userRepository.changeScore(rating.getUser().getId(), Constants.RATING_COST);
    }

    @Override
    public List<Rating> getRatingsByUserAndPostId(Integer userId, Integer postId) {
        return ratingRepository.findRatingsByUserIdAndPostId(userId,postId);
    }
}
