package com.cms.edu.service;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.cms.edu.domain.Enrollment;


public interface EnrollmentService {

    public List<Enrollment> findAllEnrollments();
    public List<Enrollment> findEnrollmentsByCourseId(final long courseId);
    public List<Enrollment> findEnrollmentsByUserId(final long userId);
    public Enrollment save(final long userId,final long courseId, final Enrollment enrollment);
    public Enrollment update(final long userId,final long courseId, final long enrollId, final Enrollment enrollment);
    public ResponseEntity<?> delete(final long userId,final long courseId, final long enrollId);
    
}