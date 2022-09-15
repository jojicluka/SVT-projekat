package com.redditclonesvt.redditclonesvt.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate creationDate;

    private int karma;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subreddit subreddit;
}
