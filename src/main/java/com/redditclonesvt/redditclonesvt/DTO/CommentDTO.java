package com.redditclonesvt.redditclonesvt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    //private String user;
    private String text;

    public long postId(){
        return postId();
    }
}
