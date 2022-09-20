package com.cms.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
 
import java.util.Arrays;
import java.util.HashSet;
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
import com.cms.edu.domain.CourseContent;
import com.cms.edu.domain.User;
import com.cms.edu.repository.CourseContentRepository;
import com.cms.edu.repository.CourseRepository;
import com.cms.edu.repository.EnrollmentRepository;
import com.cms.edu.repository.UserRepository;
import com.cms.edu.security.IAuthenticationFacade;
import com.cms.edu.service.CourseContentServiceImpl;


@ExtendWith(MockitoExtension.class)
public class CourseContentServiceImplTest {

	@InjectMocks
	CourseContentServiceImpl courseContentService;

	@Mock
	private CourseContentRepository courseContentRepository;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private EnrollmentRepository enrollmentRepository;
	
	@Mock
	private IAuthenticationFacade authenticationFacade;
	
	@Mock
	private UserRepository userRepository;

    @Test
    public void testFindCourseContentsById()
    {
        when(courseContentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(new CourseContent()));
        Optional<CourseContent> courseContent = courseContentService.findCourseContentsById(0);
        assertNotNull(courseContent);
    }
    
    @Test
    public void testFindByCourseId()
    {
        User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(courseContentRepository.findByCourseId(ArgumentMatchers.<Long>any())).thenReturn(Arrays.asList(new CourseContent()));
        assertEquals(1,courseContentService.findByCourseId(1).size());
    }
    
    @Test
    public void testFindByName()
    {
        Set<CourseContent> courseContent = new HashSet<>(Arrays.asList(new CourseContent()));
        when(courseContentRepository.findByName(ArgumentMatchers.<String>any())).thenReturn(courseContent);
        Set<CourseContent> courseContentLst = courseContentService.findByName("test");
        assertEquals(1, courseContentLst.size());
    }
    
    @Test
    public void testSave()
    {
    	User userOne = new User();
        userOne.setId(1L);
    	Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Course course = new Course();
        course.setUser(userOne);
        when(courseRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(course));
        when(courseContentRepository.save(ArgumentMatchers.<CourseContent>any())).thenReturn(new CourseContent());
        assertNotNull(courseContentService.save(1,new CourseContent()));
    }
    
    @Test
    public void testUpdate()
    {
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        when(courseRepository.existsById(ArgumentMatchers.<Long>any())).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        CourseContent cc = new CourseContent();
        Course course = new Course();
        course.setUser(userOne);
        cc.setCourse(course);
        when(courseContentRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(Optional.of(cc));
        when(courseContentRepository.save(ArgumentMatchers.<CourseContent>any())).thenReturn(new CourseContent());
        assertNotNull(courseContentService.update(1,1,new CourseContent()));
    }
    
    @Test
    public void testDelete()
    {
    	
    	User userOne = new User();
        userOne.setId(1L);
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        Authentication authentication = mock(Authentication.class);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        CourseContent cc = new CourseContent();
        Course course = new Course();
        course.setUser(userOne);
        cc.setCourse(course);
        when(courseContentRepository.findByIdAndCourseId(ArgumentMatchers.<Long>any(), ArgumentMatchers.<Long>any())).thenReturn(Optional.of(cc));
        doNothing().when(courseContentRepository).delete(ArgumentMatchers.<CourseContent>any());
        assertNotNull(courseContentService.delete(1,1));
    }
}