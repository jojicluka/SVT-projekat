package com.redditclonesvt.redditclonesvt.Controller;

import com.redditclonesvt.redditclonesvt.DTO.EditedPostDTO;
import com.redditclonesvt.redditclonesvt.DTO.PostDTO;
import com.redditclonesvt.redditclonesvt.Model.Post;
import com.redditclonesvt.redditclonesvt.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> post(@RequestBody PostDTO postDTO){
        PostDTO createdPost = postService.save(postDTO);
        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Post> editPost(@PathVariable("id") Long id, @RequestBody EditedPostDTO postDTO){
        Post post = postService.editPost(postDTO, id);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id){
        return status(HttpStatus.OK).body(postService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getPosts());
    }
}
