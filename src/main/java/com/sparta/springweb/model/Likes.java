package com.sparta.springweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "posts_id")
    @ManyToOne
    private Posts posts;

    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"postsList"})
    @ManyToOne
    private User user;


    public Likes(Posts posts, User user){
        this.posts = posts;
        this.user = user;
    }

}