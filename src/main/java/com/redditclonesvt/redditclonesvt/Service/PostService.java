package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.DTO.EditedPostDTO;
import com.redditclonesvt.redditclonesvt.DTO.PostDTO;
import com.redditclonesvt.redditclonesvt.Model.Feedback;
import com.redditclonesvt.redditclonesvt.Model.Subreddit;
import com.redditclonesvt.redditclonesvt.Repo.FeedbackRepository;
import com.redditclonesvt.redditclonesvt.Repo.PostRepository;
import com.redditclonesvt.redditclonesvt.Repo.SubredditRepository;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import com.redditclonesvt.redditclonesvt.Model.Post;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FeedbackRepository feedbackRepository;


    public PostDTO save(PostDTO postDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        User p = (User) auth.getPrincipal();

        //String username = "josjedan";
        Subreddit subreddit = subredditRepository.findByName(postDTO.getSubreddit()).orElseGet(null);
        com.redditclonesvt.redditclonesvt.Model.User user = userRepository.findByUsername(p.getUsername()).orElseGet(null);
        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user);
        post.setCreationDate(LocalDate.now());
        post.setSubreddit(subreddit);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    public void deletePost(long id){
        postRepository.deleteById(id);
        feedbackRepository.deleteForPost(id);
    }

    public Post editPost(EditedPostDTO postDTO, Long id){
        Post post = postRepository.findById(id).orElseGet(null);
        if(postDTO.getTitle() != null){
            post.setTitle(postDTO.getTitle());
        }
        if(postDTO.getContent() != null){
            post.setContent(postDTO.getContent());
        }
        postRepository.save(post);
        return post;
    }

    public Post findById(long id){
        return postRepository.findById(id).orElseGet(null);
    }

    public List<Post> getPosts(){
        List<Post> posts = new ArrayList<>(postRepository.findAll());

        List<Feedback> reactions = feedbackRepository.findAll();

        for (Post i : posts) {
            for (Feedback j : reactions) {
                if (i.getId() == j.getPost().getId()) {
                    if (j.getFeedbackType().name().equals("UPVOTE")) {
                        i.setKarma(i.getKarma()+1);
                    } else {
                        i.setKarma(i.getKarma()-1);
                    }
                }
            }
        }

        return posts;
    }
}
