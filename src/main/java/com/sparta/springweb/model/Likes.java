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

    @JoinColumn(name = "contents_id")
    @ManyToOne
    private Contents contents;

    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"contentsList"})
    @ManyToOne
    private User user;


    public Likes(Contents contents, User user){
        this.contents = contents;
        this.user = user;
    }

}
