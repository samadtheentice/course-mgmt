package com.cms.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cms.edu.domain.Course;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.exception.UserNotFoundException;
import com.cms.edu.service.CourseService;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value ="/courses", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER') or hasAuthority('STUDENT_USER')")
    public List<Course> listCourses(){
        return courseService.findAllCourses();
    }
    
    @RequestMapping(value ="/user/{userId}/courses", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public List<Course> listCoursesByUserId(@PathVariable("userId") long userId) throws UserNotFoundException{
        return courseService.findAllCoursesByUserId(userId);
    }
        
    @RequestMapping(value = "/user/{userId}/courses", method = RequestMethod.POST,produces="application/json")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public Course createCourse(@PathVariable("userId") long userId, @RequestBody Course course) {
      return courseService.save(userId, course);
    }
    
    @RequestMapping(value = "/user/{userId}/courses/{courseId}", method = RequestMethod.PUT,produces="application/json")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public Course updateCourse(@PathVariable("userId") long userId, @PathVariable("courseId") long courseId, @RequestBody Course course)  throws EntityNotFoundException, UserNotFoundException{
      return courseService.update(userId, courseId, course);
    }
    
    @RequestMapping(value = "/user/{userId}/courses/{courseId}", method = RequestMethod.DELETE,produces="application/json")
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("userId") long userId, @PathVariable("courseId") long courseId)  throws EntityNotFoundException, UserNotFoundException{
      courseService.delete(userId, courseId);
      return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}