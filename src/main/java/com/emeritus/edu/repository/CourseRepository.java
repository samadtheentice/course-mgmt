package com.emeritus.edu.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.emeritus.edu.domain.Course;

/**
 * 
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
	Set<Course> findByName(final String name);
	List<Course> findByUserId(final long userId);
}