package com.redditclonesvt.redditclonesvt.Repo;

import com.redditclonesvt.redditclonesvt.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
}
