package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.DTO.SubredditDTO;
import com.redditclonesvt.redditclonesvt.Model.Subreddit;
import com.redditclonesvt.redditclonesvt.Repo.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Transactional
    public SubredditDTO save(SubredditDTO subredditDTO){
        Subreddit subreddit = mapToDTO(subredditDTO);
        Subreddit saved = subredditRepository.save(subreddit);
        subredditDTO.setId(saved.getId());
        return subredditDTO;
    }

    private Subreddit mapToDTO(SubredditDTO subredditDTO) {
        return Subreddit.builder().name(subredditDTO.getName()).description(subredditDTO.getDescription()).creationDate(LocalDate.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDTO> getSubreddits(){
        return subredditRepository.findAll().stream().map(this::mapSubredditDTO).collect(Collectors.toList());
    }

    private SubredditDTO mapSubredditDTO(Subreddit subreddit) {
        return SubredditDTO.builder().name(subreddit.getName())
                .id(subreddit.getId()).build();
    }

    public Subreddit findSubreddit(long id){
        return subredditRepository.findById(id).orElseGet(null);
    }
}
