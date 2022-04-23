package com.ddquin.simplefourms.repository;

import com.ddquin.simplefourms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {

}
