package com.cms.edu.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.cms.edu.domain.Assignment;

/**
 * 
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
	Set<Assignment> findByName(String name);
	List<Assignment> findByCourseContentId(Long contentid);
	Optional<Assignment> findByIdAndCourseContentId(Long id, Long contentid);
}