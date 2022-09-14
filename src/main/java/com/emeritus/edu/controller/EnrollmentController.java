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

import com.emeritus.edu.domain.Enrollment;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.service.EnrollmentService;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @RequestMapping(value ="/enrollments", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public List<Enrollment> getAllEnrollments(){
        return enrollmentService.findAllEnrollments();
    }
    
    @RequestMapping(value ="/course/{courseId}/enrollments", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('INSTRUCTOR_USER')")
    public List<Enrollment> getAllEnrollmentsByCourseId(@PathVariable (value = "courseId") Long courseId) throws UserNotFoundException{
        return enrollmentService.findEnrollmentsByCourseId(courseId);
    }
    
    @RequestMapping(value ="/user/{userId}/enrollments", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public List<Enrollment> getAllEnrollmentsByUserId(@PathVariable (value = "userId") Long userId) throws UserNotFoundException{
        return enrollmentService.findEnrollmentsByUserId(userId);
    }
    
    @PostMapping("/user/{userId}/course/{courseId}/enrollments")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public Enrollment createEnrollment(@PathVariable (value = "userId") Long userId, @PathVariable (value = "courseId") Long courseId,
                                 @RequestBody Enrollment enrollment) throws EntityNotFoundException{
    	return enrollmentService.save(userId, courseId, enrollment);
    }
    
    @PutMapping("/user/{userId}/course/{courseId}/enrollments/{enrollId}")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public Enrollment updateEnrollment(@PathVariable (value = "userId") Long userId,
                                 @PathVariable (value = "courseId") Long courseId,
                                 @PathVariable (value = "enrollId") Long enrollId,
                                 @RequestBody Enrollment enrollment) throws EntityNotFoundException, UserNotFoundException{
    	return enrollmentService.update(userId, courseId,enrollId, enrollment);
    }
    
    @DeleteMapping("/user/{userId}/course/{courseId}/enrollments/{enrollId}")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public ResponseEntity<?> deleteEnrollment(@PathVariable (value = "userId") Long userId,
            @PathVariable (value = "courseId") Long courseId,
            @PathVariable (value = "enrollId") Long enrollId)  throws EntityNotFoundException, UserNotFoundException{
      return enrollmentService.delete(userId, courseId, enrollId);
    }
}