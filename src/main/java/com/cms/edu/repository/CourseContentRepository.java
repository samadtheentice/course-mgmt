package com.cms.edu.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.cms.edu.domain.CourseContent;

/**
 * 
 */
public interface CourseContentRepository extends CrudRepository<CourseContent, Long> {
	Set<CourseContent> findByName(String name);
	List<CourseContent> findByCourseId(Long courseId);
	Optional<CourseContent> findByIdAndCourseId(Long id, Long courseId);
}