package com.ddquin.simplefourms.repository;

import com.ddquin.simplefourms.model.Thread;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {}
