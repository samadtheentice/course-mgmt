package com.cms.edu.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.cms.edu.domain.CourseContent;

/**
 * 
 */
public interface CourseContentService {
    
    public List<CourseContent> findByCourseId(final long courseId);
    
    public Set<CourseContent> findByName(final String name);
    
    public CourseContent save(final long courseId, final CourseContent courseContent);
    
    public CourseContent update(final long courseId,final long contentId, final CourseContent courseContent);
    
    public ResponseEntity<?> delete(final long contentId, final long courseId);
    
}