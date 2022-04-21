package com.ddquin.simplefourms.repository;

import com.ddquin.simplefourms.model.Comment;
import com.ddquin.simplefourms.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    List<Thread> findBySectionId(Long sectionId);

    @Transactional
    void deleteBySectionId(long sectionId);
}
