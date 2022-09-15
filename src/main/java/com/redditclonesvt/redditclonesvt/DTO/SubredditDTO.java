package com.redditclonesvt.redditclonesvt.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDTO {
    private Long id;
    private String name;
    private String description;
    //private LocalDate creationDate;

}
