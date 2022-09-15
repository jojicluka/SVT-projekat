package com.redditclonesvt.redditclonesvt.Repo;

import com.redditclonesvt.redditclonesvt.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query(value = "SELECT * FROM feedback WHERE feedback.post_id = ?1", nativeQuery = true)
    List<Feedback> findFeedback(long postId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE feedback SET feedback.feedback_type = ?1 WHERE feedback.id = ?2", nativeQuery = true)
    void changeFeedback(String s, int reactId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM feedback WHERE post_id = ?1", nativeQuery = true)
    void deleteForPost(long id);


}
