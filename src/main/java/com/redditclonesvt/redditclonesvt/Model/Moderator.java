package com.redditclonesvt.redditclonesvt.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Moderator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subreddit subreddit;

    public Moderator(User user, Subreddit subreddit){
        this.user = user;
        this.subreddit = subreddit;
    }
}
