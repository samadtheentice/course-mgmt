package com.cms.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
 
import java.util.ArrayList;
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

import com.cms.edu.domain.Course;
import com.cms.edu.domain.User;
import com.cms.edu.repository.CourseRepository;
import com.cms.edu.repository.UserRepository;
import com.cms.edu.security.IAuthenticationFacade;
import com.cms.edu.service.CourseServiceImpl;


@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest 
{
	@InjectMocks
	CourseServiceImpl courseService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	CourseRepository courseRepository;
      
    @Mock
    private IAuthenticationFacade authenticationFacade;
     
    @Test
    public void testFindCoursesById()
    {
        when(courseRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new Course()));
        Optional<Course> course = courseService.findCoursesById(0);
          
        assertNotNull(course);
    }
    
    @Test
    public void testfindAllCoursesByUserId()
    {
        User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        List<Course> courseLst = new ArrayList<>();
        Course course = new Course();
        course.setName("java");
        courseLst.add(course);
        List<Course> crsLst = courseService.findAllCoursesByUserId(1);
        assertEquals(0,crsLst.size());
    }
    
    @Test
    public void testFindByName()
    {
        Set<Course> course = new HashSet<>(Arrays.asList(new Course()));
        when(courseRepository.findByName(ArgumentMatchers.<String>any())).thenReturn(course);
        Set<Course> courseLst = courseService.findByName("test");
        assertEquals(1, courseLst.size());
    }
    
    @Test
    public void testFindAllCourses()
    {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(new Course()));
        List<Course> courseLst = courseService.findAllCourses();
        assertEquals(1, courseLst.size());
    }
    
    @Test
    public void testSave()
    {
    	Optional<User> userOne = Optional.of( new User());
    	userOne.get().setUserName("test");
        when(userRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(userOne);
        when(courseRepository.save(ArgumentMatchers.<Course>any())).thenReturn(new Course());
        assertNotNull(courseService.save(1,new Course()));
    }
    
    @Test
    public void testUpdate()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(userRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(courseRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new Course()));
        when(courseRepository.save(ArgumentMatchers.<Course>any())).thenReturn(new Course());
        assertNotNull(courseService.update(1,1,new Course()));
    }
    
    @Test
    public void testDelete()
    {
    	
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(userRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        when(courseRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        doNothing().when(courseRepository).deleteById(ArgumentMatchers.<Long>any());
        courseService.delete(1,1);
    }
}