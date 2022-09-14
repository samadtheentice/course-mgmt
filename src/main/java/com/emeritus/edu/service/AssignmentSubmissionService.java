package com.emeritus.edu.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.emeritus.edu.domain.AssignmentSubmission;

/**
 * 
 */
public interface AssignmentSubmissionService {

    public List<AssignmentSubmission> findAllAssignmentSubmissionsByUserId();
    
    public List<AssignmentSubmission> findAssignmentSubmissionsByEnrollmentId(final long enrollId);
        
    public AssignmentSubmission save(final long enrollId, final long assignmentId, final AssignmentSubmission submission);
    
    public AssignmentSubmission update(final long submissionId, final long enrollId, final AssignmentSubmission submission);
    
    public ResponseEntity<?> delete(final long submissionId, final long enrollId);
    
}