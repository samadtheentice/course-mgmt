package com.cms.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cms.edu.domain.AssignmentSubmission;

/**
 * 
 */
public interface AssignmentSubmissionRepository extends CrudRepository<AssignmentSubmission, Long> {
	
	@Query(value = "SELECT s.* FROM cms_assignment_submission s INNER JOIN cms_enrollment e ON s.enrollid= e.id WHERE e.userid = :userId", 
			  nativeQuery = true)	
	List<AssignmentSubmission> findAssignmentSubmissionsByUserId( @Param("userId") Long userId);
	List<AssignmentSubmission> findByEnrollmentId(Long enrollid);
	List<AssignmentSubmission> findByAssignmentId(Long assignmentid);
	List<AssignmentSubmission> findByEnrollmentIdAndAssignmentId(Long enrollid, Long assignmentid);
}