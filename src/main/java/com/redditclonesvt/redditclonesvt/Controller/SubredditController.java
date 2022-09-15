package com.redditclonesvt.redditclonesvt.Controller;


import com.redditclonesvt.redditclonesvt.DTO.SubredditDTO;
import com.redditclonesvt.redditclonesvt.Model.Subreddit;
import com.redditclonesvt.redditclonesvt.Service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    @Autowired
    private SubredditService ss;

    @PostMapping
    public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subredditDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(ss.save(subredditDTO));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDTO>> getSubreddits(){
       return ResponseEntity.status(HttpStatus.OK).body(ss.getSubreddits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subreddit> getSubreddit(@PathVariable ("id") long id){
        Subreddit subreddit = ss.findSubreddit(id);
        return new ResponseEntity<>(subreddit, HttpStatus.OK);
    }
}
