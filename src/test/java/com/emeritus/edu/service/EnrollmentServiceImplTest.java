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

import com.emeritus.edu.domain.Course;
import com.emeritus.edu.domain.Enrollment;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.repository.CourseContentRepository;
import com.emeritus.edu.repository.CourseRepository;
import com.emeritus.edu.repository.EnrollmentRepository;
import com.emeritus.edu.repository.RoleRepository;
import com.emeritus.edu.repository.UserRepository;
import com.emeritus.edu.security.IAuthenticationFacade;


@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceImplTest {

	@InjectMocks
	EnrollmentServiceImpl enrollmentService;

	@Mock
    private CourseContentRepository courseContentRepository;
    
	@Mock
    private CourseRepository courseRepository;
    
	@Mock
    private UserRepository userRepository;
    
	@Mock
    private RoleRepository roleRepository;
    
	@Mock
    private EnrollmentRepository enrollmentRepository;
    
	@Mock
    private IAuthenticationFacade authenticationFacade; 

    @Test
    public void testFindAllEnrollments()
    {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(new Enrollment()));
        assertEquals(1, enrollmentService.findAllEnrollments().size());
    }
    
    @Test
    public void testFindEnrollmentsByCourseId()
    {
        User userOne = new User();
        userOne.setId(1L);
        Optional<Course> course = Optional.of(new Course());
        course.get().setUser(userOne);
        
        
        when(courseRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(courseRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(course);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(enrollmentRepository.findByCourseId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        assertEquals(1,enrollmentService.findEnrollmentsByCourseId(1).size());
    }
    
    @Test
    public void testFindEnrollmentsByUserId()
    {
    	User userOne = new User();
        userOne.setId(1L);
    	Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(enrollmentRepository.findByUserId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        assertEquals(1, enrollmentService.findEnrollmentsByUserId(1L).size());
    }
    
    @Test
    public void testFindEnrollmentByUserIdAndCourseId()
    {
        when(enrollmentRepository.findByUserIdAndCourseId(ArgumentMatchers.<Long>any(), ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new Enrollment()));
        assertEquals(1, enrollmentService.findEnrollmentByUserIdAndCourseId(1,1).size());
    }
    
    @Test
    public void testSave()
    {
        when(courseRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new Course()));
        when(userRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new User()));
        when(enrollmentRepository.save(ArgumentMatchers.<Enrollment>any())).thenReturn(new Enrollment());
        assertNotNull(enrollmentService.save(1,1,new Enrollment()));
    }
    
    @Test
    public void testUpdate()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(courseRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(userRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(enrollmentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new Enrollment()));
        when(enrollmentRepository.save(ArgumentMatchers.<Enrollment>any())).thenReturn(new Enrollment());
        assertNotNull(enrollmentService.update(1,1,1,new Enrollment()));
    }
    
    @Test
    public void testDelete()
    {
    	
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(courseRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(userRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(enrollmentRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        doNothing().when(enrollmentRepository).deleteById(ArgumentMatchers.<Long>any());
        assertNotNull(enrollmentService.delete(1,1,1));
    }
}