package com.redditclonesvt.redditclonesvt.DTO;

import com.redditclonesvt.redditclonesvt.Model.FeedbackEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private String feedback;
    private Long postId;
    private String user;
}
