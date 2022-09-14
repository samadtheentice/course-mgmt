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

import com.emeritus.edu.domain.AssignmentSubmission;
import com.emeritus.edu.exception.EntityNotFoundException;
import com.emeritus.edu.exception.UserNotFoundException;
import com.emeritus.edu.service.AssignmentSubmissionService;

import java.util.List;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class AssignmentSubmissionController {
    @Autowired
    private AssignmentSubmissionService assignmentSubmissionService;

    @RequestMapping(value ="/submissions", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public List<AssignmentSubmission> getAllSubmission(){
        return assignmentSubmissionService.findAllAssignmentSubmissionsByUserId();
    }
    
    @RequestMapping(value ="/enrollment/{enrollId}/submissions", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public List<AssignmentSubmission> getAllSubmissionByEnrollmentId(@PathVariable (value = "enrollId") Long enrollId) throws UserNotFoundException{
        return assignmentSubmissionService.findAssignmentSubmissionsByEnrollmentId(enrollId);
    }
    
    @PostMapping("/enrollment/{enrollId}/assignment/{assignmentId}/submissions")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public AssignmentSubmission createSubmission(@PathVariable (value = "enrollId") Long enrollId, @PathVariable (value = "assignmentId") Long assignmentId,
                                 @RequestBody AssignmentSubmission submission) throws EntityNotFoundException, UserNotFoundException{
    	return assignmentSubmissionService.save(enrollId, assignmentId, submission);
    }
    
    @PutMapping("/enrollment/{enrollId}/submissions/{submissionId}")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public AssignmentSubmission updateSubmission(@PathVariable (value = "enrollId") Long enrollId,
                                 @PathVariable (value = "submissionId") Long submissionId,
                                 @RequestBody AssignmentSubmission submission) throws EntityNotFoundException, UserNotFoundException{
    	return assignmentSubmissionService.update(submissionId, enrollId, submission);
    }
    
    @DeleteMapping("/enrollment/{enrollId}/submissions/{submissionId}")
    @PreAuthorize("hasAuthority('STUDENT_USER')")
    public ResponseEntity<?> deleteSubmission(@PathVariable (value = "submissionId") Long submissionId,
            @PathVariable (value = "enrollId") Long enrollId)  throws EntityNotFoundException, UserNotFoundException{
      return assignmentSubmissionService.delete(submissionId, enrollId);
    }
}