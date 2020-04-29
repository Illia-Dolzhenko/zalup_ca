package com.hotmail.dolzhik.zalup_ca.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CommentUpVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Comment comment;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private  User user;
}
