package com.emeritus.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.emeritus.edu.domain.Assignment;
import com.emeritus.edu.domain.AssignmentSubmission;
import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.CourseContent;
import com.emeritus.edu.domain.Enrollment;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.repository.AssignmentRepository;
import com.emeritus.edu.repository.AssignmentSubmissionRepository;
import com.emeritus.edu.repository.EnrollmentRepository;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;


@ExtendWith(MockitoExtension.class)
public class AssignmentSubmissionServiceImplTest {

	@InjectMocks
	AssignmentSubmissionServiceImpl assignmentSubmissionService;

	@Mock
    private AssignmentRepository assignmentRepository;
   
	@Mock
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    
	@Mock
    private EnrollmentRepository enrollmentRepository;
    
	@Mock
    private UserRepository userRepository;
    
	@Mock
    private IAuthenticationFacade authenticationFacade;

    @Test
    public void testFindAllAssignmentSubmissionsByUserId()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(assignmentSubmissionRepository.findAssignmentSubmissionsByUserId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new AssignmentSubmission()));
        assertEquals(1, assignmentSubmissionService.findAllAssignmentSubmissionsByUserId().size());
    }
    
    @Test
    public void testFindAssignmentSubmissionsByEnrollmentId()
    {
        User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(enrollmentRepository.findByIdAndUserId(ArgumentMatchers.<Long>any(),ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        when(assignmentSubmissionRepository.findByEnrollmentId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new AssignmentSubmission()));
        assertEquals(1,assignmentSubmissionService.findAssignmentSubmissionsByEnrollmentId(1).size());
    }
    
    @Test
    public void testSave()
    {
    	User userOne = new User();
        userOne.setId(1L);
        Enrollment enrollment = new Enrollment();
    	Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(assignmentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new Assignment()));
        when(enrollmentRepository.findByIdAndUserId(ArgumentMatchers.<Long>any(), ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(enrollment));
        when(assignmentSubmissionRepository.save(ArgumentMatchers.<AssignmentSubmission>any())).thenReturn(new AssignmentSubmission());
        assertNotNull(assignmentSubmissionService.save(1L, 1L, new AssignmentSubmission()));
    }
    
    @Test
    public void testUpdate()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        AssignmentSubmission assignSub = new AssignmentSubmission();
      
        
        when(enrollmentRepository.findByIdAndUserId(ArgumentMatchers.<Long>any(),ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        when(assignmentSubmissionRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new AssignmentSubmission()));
        when(assignmentSubmissionRepository.save(ArgumentMatchers.<AssignmentSubmission>any())).thenReturn(new AssignmentSubmission());
        assertNotNull(assignmentSubmissionService.update(1,1,new AssignmentSubmission()));
    }
    
    @Test
    public void testDelete()
    {
    	
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        Assignment assign = new Assignment();
        CourseContent cc = new CourseContent();
        assign.setCourseContent(cc);
        Course course = new Course();
        course.setUser(userOne);
        cc.setCourse(course);
        when(enrollmentRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(assignmentSubmissionRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(enrollmentRepository.findByIdAndUserId(ArgumentMatchers.<Long>any(), ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        doNothing().when(assignmentSubmissionRepository).deleteById(ArgumentMatchers.<Long>any());
        assertNotNull(assignmentSubmissionService.delete(1,1));
    }
}