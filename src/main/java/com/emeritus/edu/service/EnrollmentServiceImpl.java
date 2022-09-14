package com.emeritus.edu.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.CourseContent;
import com.emeritus.edu.domain.Enrollment;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.repository.RoleRepository;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;
import com.emeritus.edu.repository.CourseContentRepository;
import com.emeritus.edu.repository.CourseRepository;
import com.emeritus.edu.repository.EnrollmentRepository;

/**
 * 
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private CourseContentRepository courseContentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade; 
    
    @Override
    public List<Enrollment> findAllEnrollments() {
        return (List<Enrollment>)enrollmentRepository.findAll();
    }
    
    /*
     * find enrollments by courseId
     */
    @Override
    public List<Enrollment> findEnrollmentsByCourseId(final long courseId){
    	
    	 if(!courseRepository.existsById(courseId)) {
             throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
         }

    	 
    	Optional<Course> course = courseRepository.findById(courseId);
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(course.get().getUser().getId() != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}
    	
    	return enrollmentRepository.findByCourseId(courseId);
    }
    
    /*
     * Get course enrollments by userid
     */
    @Override
    public List<Enrollment> findEnrollmentsByUserId(final long userId){
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}
    	return enrollmentRepository.findByUserId(userId);
    }
    
    /*
     * find enrollments by userid and courseid
     */
    public List<Enrollment> findEnrollmentByUserIdAndCourseId(final long userId, final long courseId){
    	return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }
    
    /*
     * Create enrollment
     */
    @Override
    public Enrollment save(final long userId, final long courseId, final Enrollment enrollment) {
    	
    	Optional<User> user = userRepository.findById(userId);
    	Optional<Course> course = courseRepository.findById(courseId);
    	
    	if(!user.isPresent()) {
            throw new EntityNotFoundException(User.class, "id", String.valueOf(userId));
        }else if(! course.isPresent()) {
            throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
        }
    	
		enrollment.setCourse(course.get());
		enrollment.setUser(user.get());
		enrollment.setEnrollDate(Instant.now());
		return enrollmentRepository.save(enrollment);
    }
    
    /*
     * update the enrollment
     */
    @Override
    public Enrollment update(final long userId,final long courseId, final long enrollId, final Enrollment enrollment) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(User.class, "id", String.valueOf(userId));
        }else if(!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
        }
    	
    	if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}

        return enrollmentRepository.findById(enrollId).map(_enrollment -> {
        	_enrollment.setEnrollDate(Instant.now());
            return enrollmentRepository.save(_enrollment);
        }).orElseThrow(() -> new EntityNotFoundException(Enrollment.class, "id", String.valueOf(courseId)));
    }
    
    /*
     * delete the enrollment
     */
    @Override
    public ResponseEntity<?> delete(final long userId,final long courseId, final long enrollId) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}
    	
    	if(!userRepository.existsById(userId)) {
    		throw new EntityNotFoundException(User.class, "id", String.valueOf(userId));
    	}else if(!courseRepository.existsById(courseId)) {
    		throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
    	}else if(!enrollmentRepository.existsById(enrollId)) {
    		throw new EntityNotFoundException(Enrollment.class, "id", String.valueOf(enrollId));
    	}
    	
		enrollmentRepository.deleteById(enrollId);
        return ResponseEntity.ok().build();
    }
    
}