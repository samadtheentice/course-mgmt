package com.emeritus.edu.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.Assignment;
import com.emeritus.edu.domain.CourseContent;

/**
 * 
 */
public interface AssignmentService {

    public List<Assignment> findAllCourseAssignments();
    
    public List<Assignment> findByContentId(final long contentId);
    
    public Set<Assignment> findByName(final String name);
    
    public Assignment save(final long contentId, final Assignment courseAssignment);
    
    public Assignment update(final long assignmentId,final long contentId, final Assignment courseAssignment);
    
    public ResponseEntity<?> delete(final long assignmentId, final long contentId);
    
}