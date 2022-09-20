package com.cms.edu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.cms.edu.domain.Assignment;
import com.cms.edu.domain.CourseContent;
import com.cms.edu.domain.Enrollment;
import com.cms.edu.domain.User;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.exception.UserNotFoundException;
import com.cms.edu.repository.AssignmentRepository;
import com.cms.edu.repository.CourseContentRepository;
import com.cms.edu.repository.EnrollmentRepository;
import com.cms.edu.repository.UserRepository;
import com.cms.edu.security.IAuthenticationFacade;

/**
 * 
 */
@Service
public class AssignmentServiceImpl implements AssignmentService {
	
    @Autowired
    private CourseContentRepository courseContentRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Override
    public List<Assignment> findAllCourseAssignments() {
        return (List<Assignment>)assignmentRepository.findAll();
    }

    @Override
    public Set<Assignment> findByName(final String name) {
        return assignmentRepository.findByName(name);
    }
    
    /*
     * Get assignments by contentid
     */
    public List<Assignment> findByContentId(final long contentId){
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	Optional<CourseContent> courseContent = courseContentRepository.findById(contentId);
    	
    	Map<String, Boolean> roles = new HashMap<String, Boolean>();
    	
    	//get the user role
    	for (GrantedAuthority authority : authenticationFacade.getAuthentication().getAuthorities()) {
    		roles.put(authority.getAuthority(),Boolean.TRUE);
    	}
    	
    	if(roles.containsKey("INSTRUCTOR_USER")) {
    		if(courseContent.isPresent() && courseContent.get().getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The user doesnot have permission to access thee assignments");
    		}   		
    	}else if(roles.containsKey("STUDENT_USER")) {
    		List<Enrollment> enrollments = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseContent.get().getCourse().getId());
    		if(enrollments.isEmpty()) {
       		 	throw new UserNotFoundException("The user is not enrolled to the corse to access the contents");
    		}  
    	}
    	return assignmentRepository.findByCourseContentId(contentId);
    }
    
    /*
     * save assignment by contentid
     */
    @Override
    public Assignment save(final long contentId, final Assignment courseAssignment) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	return courseContentRepository.findById(contentId).map(content -> {
    		if(content.getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The user doesnot have permission to create assignment for this course content");
    		}
    		courseAssignment.setCourseContent(content);
            return assignmentRepository.save(courseAssignment);
        }).orElseThrow(() ->  new EntityNotFoundException(Assignment.class, "id", String.valueOf(contentId)));
    }
    
    /*
     * update assignment by contentid and assignmentid
     */
    @Override
    public Assignment update(final long assignmentId,final long contentId, final Assignment courseAssignment) {
    	
        if(!courseContentRepository.existsById(contentId)) {
            throw new EntityNotFoundException(CourseContent.class, "id", String.valueOf(contentId));
        }

        User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
        
        return assignmentRepository.findById(assignmentId).map(assignment -> {
        	
        	if(assignment.getCourseContent().getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The user doesnot have permission to update assignment for this course content");
    		}
        	
        	assignment.setName(courseAssignment.getName());
        	assignment.setDescription(courseAssignment.getDescription());;
        	assignment.setDuration(courseAssignment.getDuration());
        	assignment.setAssignmenttext(courseAssignment.getAssignmenttext());
        	assignment.setUrl(courseAssignment.getUrl());
    		
            return assignmentRepository.save(assignment);
        }).orElseThrow(() -> new EntityNotFoundException(Assignment.class, "id", String.valueOf(assignmentId)));
    }
    
    /*
     * delete assignment by contentid and assignment id
     */
    @Override
    public ResponseEntity<?> delete(final long assignmentId, final long contentId) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	return assignmentRepository.findByIdAndCourseContentId(assignmentId, contentId).map(assignment -> {
    		if(assignment.getCourseContent().getCourse().getUser().getId() != user.getId()) {
       		 	throw new UserNotFoundException("The User doesnot have permission to delete assignment for this course content");
    		}
    		assignmentRepository.delete(assignment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new EntityNotFoundException(CourseContent.class, "CourseAssignment not found with id " + assignmentId + " and contentId " + contentId));
    }
    
}