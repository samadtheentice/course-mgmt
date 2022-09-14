package com.emeritus.edu.controller;

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

import com.emeritus.edu.domain.CourseContent;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.service.CourseContentService;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class CourseContentController {
    @Autowired
    private CourseContentService courseContentService;
    
    @RequestMapping(value ="/course/{courseId}/contents", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER') or hasAuthority('STUDENT_USER')")
    public List<CourseContent> getAllContentsByCourseId(@PathVariable (value = "courseId") Long courseId) throws UserNotFoundException{
        return courseContentService.findByCourseId(courseId);
    }
    
    
    @PostMapping("/course/{courseId}/contents")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public CourseContent createCourseContent(@PathVariable (value = "courseId") Long courseId,
                                 @RequestBody CourseContent courseContent) throws EntityNotFoundException, UserNotFoundException{
    	return courseContentService.save(courseId, courseContent);
    }
    
    @PutMapping("/course/{courseId}/contents/{contentId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public CourseContent updateCourseContent(@PathVariable (value = "courseId") Long courseId,
                                 @PathVariable (value = "contentId") Long contentId,
                                 @RequestBody CourseContent courseContent) throws UserNotFoundException{
    	return courseContentService.update(courseId,contentId, courseContent);
    }
    
    @DeleteMapping("/course/{courseId}/contents/{contentId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public ResponseEntity<?> deleteCourseContent(@PathVariable("courseId") long courseId, @PathVariable("contentId") long contentId)  throws EntityNotFoundException, UserNotFoundException{
      return courseContentService.delete(contentId, courseId);
    }
}