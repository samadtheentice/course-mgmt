package com.cms.edu.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cms.edu.domain.Assignment;
import com.cms.edu.domain.AssignmentSubmission;
import com.cms.edu.domain.CourseContent;
import com.cms.edu.domain.Enrollment;
import com.cms.edu.domain.User;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.exception.UserNotFoundException;
import com.cms.edu.repository.AssignmentRepository;
import com.cms.edu.repository.AssignmentSubmissionRepository;
import com.cms.edu.repository.EnrollmentRepository;
import com.cms.edu.repository.UserRepository;
import com.cms.edu.security.IAuthenticationFacade;

/**
 * 
 */
@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    @Autowired
    private AssignmentRepository assignmentRepository;
   
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    /*
     * Find all Submissions by user id
     */
    @Override
    public List<AssignmentSubmission> findAllAssignmentSubmissionsByUserId() {
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
        return (List<AssignmentSubmission>)assignmentSubmissionRepository.findAssignmentSubmissionsByUserId(user.getId());
    }
    
    /*
     * Find all Submissions by enrollment id, this is to restrict users to accessing the assignments of other users assignment
     */
    public List<AssignmentSubmission> findAssignmentSubmissionsByEnrollmentId(final long enrollId){
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	//first check if the user is associated with this enrollment id 
    	List<Enrollment> enrollment = enrollmentRepository.findByIdAndUserId(enrollId, user.getId());
    	
    	if(enrollment.isEmpty()) {
    		throw new UserNotFoundException("The user is not enrolled to this course and cannot access the assignments Assignments");
    	}    	
    	return assignmentSubmissionRepository.findByEnrollmentId(enrollId);
    }    
    
    /*
     * Create assignments
     */
    @Override
    public AssignmentSubmission save(final long enrollId, final long assignmentId, final AssignmentSubmission submission) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	List<Enrollment> enrollment = enrollmentRepository.findByIdAndUserId(enrollId, user.getId());
    	
    	if(enrollment.isEmpty()) {
    		throw new UserNotFoundException("The use has not enrolled to the course to submit the assignemnt");
    	}    	
    	
    	Optional<Assignment> courseAssignment = assignmentRepository.findById(assignmentId);
    	
    	if(courseAssignment.isPresent()) {
    		submission.setAssignment(courseAssignment.get());
    		submission.setEnrollment(enrollment.get(0));
    		submission.setSubmissionDate(Instant.now());
    		return assignmentSubmissionRepository.save(submission);
    	}else {
    		throw new EntityNotFoundException(Assignment.class, "id", String.valueOf(assignmentId));
    	}
    }
    
    /*
     * 
     */
    @Override
    public AssignmentSubmission update(final long submissionId, final long enrollId, final AssignmentSubmission submission) {
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	
    	List<Enrollment> enrollment = enrollmentRepository.findByIdAndUserId(enrollId, user.getId());
    	
    	if(enrollment.isEmpty()) {
    		throw new UserNotFoundException("The user has not enrolled to the course to update the assignemnt");
    	}

        return assignmentSubmissionRepository.findById(submissionId).map(_submission -> {
        	_submission.setSubmissiontext(submission.getSubmissiontext());
            return assignmentSubmissionRepository.save(_submission);
        }).orElseThrow(() -> new EntityNotFoundException(AssignmentSubmission.class, "id", String.valueOf(submissionId)));
    }
    
    @Override
    public ResponseEntity<?> delete(final long submissionId, final long enrollId) {

    	if(!enrollmentRepository.existsById(enrollId)){
    		throw new EntityNotFoundException(Enrollment.class, "id", String.valueOf(enrollId));
    	}
    	
    	User user = userRepository.findByUserName(authenticationFacade.getAuthentication().getName());
    	List<Enrollment> enrollment = enrollmentRepository.findByIdAndUserId(enrollId, user.getId());
    	
    	if(enrollment.isEmpty()) {
    		throw new UserNotFoundException("The user has not enrolled to the course to delete the assignemnt");
    	}else if(!assignmentSubmissionRepository.existsById(submissionId)) {
    		throw new EntityNotFoundException(AssignmentSubmission.class, "id", String.valueOf(submissionId));
    	} 
    	
		assignmentSubmissionRepository.deleteById(submissionId);
        return ResponseEntity.ok().build(); 	
    }
    
}