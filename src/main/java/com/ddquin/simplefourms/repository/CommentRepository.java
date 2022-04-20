package com.ddquin.simplefourms.repository;

import com.ddquin.simplefourms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByThreadId(Long postId);

    @Transactional
    void deleteByThreadId(long threadId);
}
