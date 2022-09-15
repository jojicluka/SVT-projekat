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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @Column
    private LocalDate timestamp;

    @Column
    private boolean isExists;

    @ManyToOne
    private Post postId;

    @ManyToOne
    private User user;
}
