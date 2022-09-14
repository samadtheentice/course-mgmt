package com.emeritus.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.emeritus.edu.domain.Assignment;
import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.CourseContent;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.repository.AssignmentRepository;
import com.emeritus.edu.repository.CourseContentRepository;
import com.emeritus.edu.repository.EnrollmentRepository;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;


@ExtendWith(MockitoExtension.class)
public class AssignmentServiceImplTest {

	@InjectMocks
	AssignmentServiceImpl assignmentService;

	@Mock
    private CourseContentRepository courseContentRepository;
    
	@Mock
    private AssignmentRepository assignmentRepository;
    
	@Mock
    private IAuthenticationFacade authenticationFacade;
    
	@Mock
    private UserRepository userRepository;
    
	@Mock
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void testFindAllCourseAssignments()
    {
        when(assignmentRepository.findAll()).thenReturn(Arrays.asList(new Assignment()));
        List<Assignment> assignment = assignmentService.findAllCourseAssignments();
        assertEquals(1, assignment.size());
    }
    
    @Test
    public void testFindByContentId()
    {
        User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(assignmentRepository.findByCourseContentId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Assignment()));
        assertEquals(1,assignmentService.findByContentId(1).size());
    }
    
    @Test
    public void testFindByName()
    {
        Set<Assignment> assignment = new HashSet<>(Arrays.asList(new Assignment()));
        when(assignmentRepository.findByName(ArgumentMatchers.<String>any())).thenReturn(assignment);
        Set<Assignment> assignmentSet = assignmentService.findByName("test");
        assertEquals(1, assignmentSet.size());
    }
    
    @Test
    public void testSave()
    {
    	User userOne = new User();
        userOne.setId(1L);
        CourseContent courseContent = new CourseContent();
        Course course = new Course();
        course.setUser(userOne);
        courseContent.setCourse(course);
    	Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(courseContentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(courseContent));
        when(assignmentRepository.save(ArgumentMatchers.<Assignment>any())).thenReturn(new Assignment());
        assertNotNull(assignmentService.save(1,new Assignment()));
    }
    
    @Test
    public void testUpdate()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(courseContentRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        Assignment assign = new Assignment();
        CourseContent cc = new CourseContent();
        assign.setCourseContent(cc);
        Course course = new Course();
        course.setUser(userOne);
        cc.setCourse(course);
        when(assignmentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(assign));
        when(assignmentRepository.save(ArgumentMatchers.<Assignment>any())).thenReturn(new Assignment());
        assertNotNull(assignmentService.update(1,1,new Assignment()));
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
        when(assignmentRepository.findByIdAndCourseContentId(ArgumentMatchers.<Long>any(), ArgumentMatchers.<Long>any())).thenReturn(Optional.of(assign));
        doNothing().when(assignmentRepository).delete(ArgumentMatchers.<Assignment>any());
        assertNotNull(assignmentService.delete(1,1));
    }
}