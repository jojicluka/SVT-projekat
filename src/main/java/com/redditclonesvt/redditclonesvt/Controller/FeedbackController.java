package com.redditclonesvt.redditclonesvt.Controller;

import com.redditclonesvt.redditclonesvt.DTO.FeedbackDTO;
import com.redditclonesvt.redditclonesvt.DTO.FeedbackFetchDTO;
import com.redditclonesvt.redditclonesvt.Model.Feedback;
import com.redditclonesvt.redditclonesvt.Service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    public FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<Feedback> giveFeedback(@RequestBody FeedbackDTO feedbackDTO){
        Feedback saveReaction = feedbackService.save(feedbackDTO);
        return new ResponseEntity<>(saveReaction, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackFetchDTO> fetchKarma(@PathVariable("id") Long id){
        Long k = Long.valueOf(feedbackService.findKarma(id));
        return new ResponseEntity<FeedbackFetchDTO>(new FeedbackFetchDTO(k, id),HttpStatus.OK);
    }
}
