package com.hotmail.dolzhik.zalup_ca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rating {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    private Integer score;
    @Enumerated(EnumType.STRING)
    private RateCategory category;
}
