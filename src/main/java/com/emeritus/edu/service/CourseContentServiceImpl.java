package com.emeritus.edu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.CourseContent;
import com.emeritus.edu.domain.Enrollment;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;
import com.emeritus.edu.repository.CourseContentRepository;
import com.emeritus.edu.repository.CourseRepository;
import com.emeritus.edu.repository.EnrollmentRepository;

/**
 * 
 */
@Service
public class CourseContentServiceImpl implements CourseContentService {
	
    @Autowired
    private CourseContentRepository courseContentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Set<CourseContent> findByName(final String name) {
        return courseContentRepository.findByName(name);
    }
    
    
    /*
     * find course content by id
     */
    public Optional<CourseContent> findCourseContentsById(final long id){
    	return courseContentRepository.findById(id);
    }
    
    /*
     * find by course id
     */
    public List<CourseContent> findByCourseId(final long courseId){
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	Map<String, Boolean> roles = new HashMap<String, Boolean>();
    	
    	for (GrantedAuthority authority : authenticationFacade.getAuthentication().getAuthorities()) {
    		roles.put(authority.getAuthority(),Boolean.TRUE);
    	}
    	
    	if(roles.containsKey("INSTRUCTOR_USER")) {
    		Optional<Course> course = courseRepository.findById(courseId);
    		
    		if(course.isPresent() && course.get().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The course content cannot be access by this istructor");
    		}   		
    	}else if(roles.containsKey("STUDENT_USER")) {
    		List<Enrollment> enrollments = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseId);
    		if(enrollments.isEmpty()) {
       		 	throw new UserNotFoundException("The Student is not enrolled to the core to access its contents");
    		}  
    	}
    	
    	return courseContentRepository.findByCourseId(courseId);
    }
    
    /*
     * save course content
     */
    @Override
    public CourseContent save(final long courseId, final CourseContent courseContent) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	return courseRepository.findById(courseId).map(course -> {
    		if(course.getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The Instructore doesnot have permission to create content for this course");
    		}
    		courseContent.setCourse(course);
            return courseContentRepository.save(courseContent);
        }).orElseThrow(() ->  new EntityNotFoundException(CourseContent.class, "id", String.valueOf(courseId)));
    }
    
    /*
     * update course content
     */
    @Override
    public CourseContent update(final long courseId,final long contentId, final CourseContent courseContent) {
    	
        if(!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
        }
        
        User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
        
        return courseContentRepository.findById(contentId).map(content -> {
        	if(content.getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The Instructore doesnot have permission to update content for this course");
    		}
        	
        	content.setName(courseContent.getName());
        	content.setDescription(courseContent.getDescription());;
        	content.setDuration(courseContent.getDuration());
        	content.setCourseContentText(courseContent.getCourseContentText());
    		
            return courseContentRepository.save(content);
        }).orElseThrow(() -> new EntityNotFoundException(CourseContent.class, "id", String.valueOf(contentId)));
    }
    
    /*
     * delete course contentd
     */
    @Override
    public ResponseEntity<?> delete(final long contentId, final long courseId) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	return courseContentRepository.findByIdAndCourseId(contentId, courseId).map(content -> {
    		if(content.getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The Instructore doesnot have permission to delete content for this course");
    		}
    		courseContentRepository.delete(content);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new EntityNotFoundException(CourseContent.class, "Course Content not found with id " + contentId + " and courseId " + courseId));
    }
    
}