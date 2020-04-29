package com.hotmail.dolzhik.zalup_ca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String image;
    private String text;
    private Timestamp creationDate;
    private Integer timeToLive; //hours
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Rating> ratings;
    @Transient
    private Map<RateCategory, Float> averageRatings;

    @PostLoad
    public void processRatings() {
        averageRatings = new HashMap<>();
        Map<RateCategory, Integer> numOfRatings = new HashMap<>();
        Map<RateCategory, Integer> sumOfRatings = new HashMap<>();

        ratings.forEach(rating -> {

            RateCategory category = rating.getCategory();

            if (numOfRatings.containsKey(category)) {
                int oldValue = numOfRatings.get(category);
                numOfRatings.put(category, oldValue + 1);
            } else {
                numOfRatings.put(category, 1);
            }

            if (sumOfRatings.containsKey(category)) {
                int oldValue = sumOfRatings.get(category);
                sumOfRatings.put(category, oldValue + rating.getScore());
            } else {
                sumOfRatings.put(category, rating.getScore());
            }
        });

        numOfRatings.keySet().forEach(rateCategory -> {
            averageRatings.put(rateCategory, (float) (sumOfRatings.get(rateCategory) / numOfRatings.get(rateCategory)));
        });

    }

}
