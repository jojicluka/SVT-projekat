package com.redditclonesvt.redditclonesvt.Controller;

import com.redditclonesvt.redditclonesvt.DTO.CommentDTO;
import com.redditclonesvt.redditclonesvt.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDTO> comment(@RequestBody CommentDTO commentDTO, @PathVariable String postId){
        CommentDTO saveComment = commentService.comment(commentDTO);
        return new ResponseEntity<>(saveComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable long id){
        commentService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
