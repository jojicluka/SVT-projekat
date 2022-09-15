package com.redditclonesvt.redditclonesvt.Service;


import com.redditclonesvt.redditclonesvt.DTO.CommentDTO;
import com.redditclonesvt.redditclonesvt.Model.Comment;
import com.redditclonesvt.redditclonesvt.Model.Post;
import com.redditclonesvt.redditclonesvt.Repo.CommentRepository;
import com.redditclonesvt.redditclonesvt.Repo.PostRepository;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentDTO comment(CommentDTO commentDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User p = (User) auth.getPrincipal();

        Post post = postRepository.findById(commentDTO.postId()).orElseGet(null);
        com.redditclonesvt.redditclonesvt.Model.User user = userRepository.findByUsername(p.getUsername()).orElseGet(null);
        Comment comment = new Comment();

        comment.setText(commentDTO.getText());
        comment.setTimestamp(LocalDate.now());
        comment.setPostId(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    public void delete(long id){
        commentRepository.deleteById(id);
    }
}
