package com.cms.edu.service;

import java.util.List;
import java.util.Set;

import com.cms.edu.domain.Course;

/**
 * 
 */
public interface CourseService {

    public List<Course> findAllCourses();
    
    public List<Course> findAllCoursesByUserId(final long userId);
    public Set<Course> findByName(final String name);
    
    public Course save(final long userId, final Course course);
    
    public Course update(final long userId, final long courseId , final Course course);
    
    public void delete(final long userId, final long courseId);
    
}