package com.cms.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cms.edu.domain.Assignment;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.exception.UserNotFoundException;
import com.cms.edu.service.AssignmentService;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    /*@RequestMapping(value ="/assignment", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public List<CourseAssignment> listCourseAssignments(){
        return assignmentService.findAllCourseAssignments();
    }*/
    
    @RequestMapping(value ="/content/{contentId}/assignments", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER') or hasAuthority('STUDENT_USER')")
    public List<Assignment> getAllCourseAssignmentByContentId(@PathVariable (value = "contentId") Long contentId) throws UserNotFoundException{
        return assignmentService.findByContentId(contentId);
    }
    
    @PostMapping("/content/{contentId}/assignments")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public Assignment createCourseAssignment(@PathVariable (value = "contentId") Long contentId,
                                 @RequestBody Assignment courseAssignment) throws EntityNotFoundException, UserNotFoundException{
    	return assignmentService.save(contentId, courseAssignment);
    }
    
    @PutMapping("/content/{contentId}/assignments/{assignmentId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public Assignment updateCourseAssignment(@PathVariable (value = "contentId") Long contentId,
                                 @PathVariable (value = "assignmentId") Long assignmentId,
                                 @RequestBody Assignment courseAssignment) throws UserNotFoundException{
    	return assignmentService.update(assignmentId, contentId, courseAssignment);
    }
    
    @DeleteMapping("/content/{contentId}/assignments/{assignmentId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public ResponseEntity<?> deleteCourseAssignment(@PathVariable("contentId") long contentId, @PathVariable("assignmentId") long assignmentId)  throws EntityNotFoundException, UserNotFoundException{
      return assignmentService.delete(assignmentId, contentId);
    }
}