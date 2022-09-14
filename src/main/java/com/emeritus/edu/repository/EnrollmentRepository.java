package com.emeritus.edu.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.emeritus.edu.domain.Enrollment;

/**
 * 
 */
public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
	List<Enrollment> findByCourseId(Long courseid);
	List<Enrollment> findByUserId(Long userid);
	List<Enrollment> findByUserIdAndCourseId(Long userid, Long courseid);
	List<Enrollment> findByIdAndUserId(Long id, Long userid);
}