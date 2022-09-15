package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.DTO.FeedbackDTO;
import com.redditclonesvt.redditclonesvt.Model.Feedback;
import com.redditclonesvt.redditclonesvt.Model.FeedbackEnum;
import com.redditclonesvt.redditclonesvt.Model.Post;
import com.redditclonesvt.redditclonesvt.Model.User;
import com.redditclonesvt.redditclonesvt.Repo.FeedbackRepository;
import com.redditclonesvt.redditclonesvt.Repo.PostRepository;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> findAll(){
        return feedbackRepository.findAll();
    }

    public Feedback save(FeedbackDTO feedbackDTO){
        FeedbackEnum reactionType = FeedbackEnum.valueOf(feedbackDTO.getFeedback());
        long postId = feedbackDTO.getPostId();

        User user = userRepository.findByUsername(feedbackDTO.getUser()).orElseGet(null);
        Post post = postRepository.findById(postId).orElseGet(null);
        Feedback feedback = new Feedback();
        feedback.setFeedbackType(reactionType);
        feedback.setUser(user);
        feedback.setPost(post);

        List<Feedback> all = findAll();
        boolean exists = false;
        boolean change = false;
        Feedback changeFeedback = new Feedback();

        for (Feedback f : all){
            if(f.getUser().getId() == feedback.getUser().getId()
                    && f.getPost().getId() == feedback.getPost().getId()
                    && f.getFeedbackType().equals(feedback.getFeedbackType())){
                exists = true;
                break;
            }
            if(f.getUser().getId() == feedback.getUser().getId()
                    && f.getPost().getId() == feedback.getPost().getId()){
                change = true;
                changeFeedback = f;
            }
        }

        if(exists){
            return feedback;
        } else if(change){
            if (changeFeedback.getFeedbackType().equals(FeedbackEnum.UPVOTE)){
                feedbackRepository.changeFeedback("DOWNVOTE", (int) changeFeedback.getId());
            } if (changeFeedback.getFeedbackType().equals(FeedbackEnum.DOWNVOTE)){
                feedbackRepository.changeFeedback("UPVOTE", (int) changeFeedback.getId());
            }
        } else{
            feedbackRepository.save(feedback);
        }
        return feedback;
    }

    public void deleteReact(long id){
        feedbackRepository.deleteForPost(id);
    }

    public int findKarma(Long id){
        int karma = 0;

        List<Feedback> all =feedbackRepository.findFeedback(id);
        for (Feedback r : all){
            if(r.getFeedbackType().equals(FeedbackEnum.DOWNVOTE)){
                karma -= 1;
            }if(r.getFeedbackType().equals(FeedbackEnum.UPVOTE)){
                karma += 1;
            }
        }
        return karma;
    }
}
