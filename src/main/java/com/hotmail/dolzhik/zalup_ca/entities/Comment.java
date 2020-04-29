package com.hotmail.dolzhik.zalup_ca.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private String text;
    private Timestamp created;
    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    List<CommentUpVote> upVotes;
    @Transient
    private Integer numberOfUpVotes;

    @PostLoad
    public void countUpVotes(){
        numberOfUpVotes = upVotes.size();
    }

}
