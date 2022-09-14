package com.emeritus.edu.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.edu.domain.User;
import com.emeritus.edu.domain.Course;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;
import com.emeritus.edu.repository.CourseRepository;

/**
 * 
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade; 
    
    public Optional<Course> findCoursesById(final long id){
    	return courseRepository.findById(id);
    }
    
    public List<Course> findAllCoursesByUserId(final long userId){
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}

    	return courseRepository.findByUserId(userId);
    }

    /*
     * find course by name
     */
    @Override
    public Set<Course> findByName(final String name) {
        return courseRepository.findByName(name);
    }

    /*
     * find all courses
     */
    @Override
    public List<Course> findAllCourses() {
        return (List<Course>)courseRepository.findAll();
    }
    
    /*
     * save course
     */
    @Override
    public Course save(final long userId, final Course course) {
    	 return userRepository.findById(userId).map(user -> {
    		 course.setUser(user);
             return courseRepository.save(course);
         }).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(userId)));
    }
    
    /*
     * Update course
     */
    @Override
    public Course update(final long userId, final long courseId , final Course course) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(User.class, "id", String.valueOf(userId));
        }else if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	} 
    	
    	Optional<Course> courseData = courseRepository.findById(courseId);
    	if(!courseData.isPresent()) {
    		throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
    	}
    		
		Course _course = courseData.get();
		_course.setName(course.getName());
		_course.setDescription(course.getDescription());;
		_course.setDuration(course.getDuration());
		_course.setCourseContents(course.getCourseContents());
		return courseRepository.save(_course);
    }
    
    /*
     * delete course
     */
    @Override
    public void delete(final long userId, final long courseId) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	if(userId != user.getId()) {
    		 throw new UserNotFoundException("No right for this user to perform the action");
    	}
    	
    	if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(User.class, "id", String.valueOf(userId));
        }else if(!courseRepository.existsById(courseId) ) {
            throw new EntityNotFoundException(Course.class, "id", String.valueOf(courseId));
        }
    	
    	courseRepository.deleteById(courseId);
    }
}