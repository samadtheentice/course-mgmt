package com.cms.edu.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.cms.edu.domain.Assignment;
import com.cms.edu.domain.Course;
import com.cms.edu.domain.CourseContent;

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