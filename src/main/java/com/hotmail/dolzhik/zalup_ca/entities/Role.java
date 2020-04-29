package com.hotmail.dolzhik.zalup_ca.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private List<User> users;

}
