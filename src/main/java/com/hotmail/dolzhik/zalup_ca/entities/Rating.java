package com.hotmail.dolzhik.zalup_ca.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Post post;
    private Float score;
    private String type;
}
