package com.cms.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.cms.edu.domain.Role;
import com.cms.edu.domain.User;
import com.cms.edu.repository.UserRepository;
import com.cms.edu.service.AppUserDetailsService;


@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest 
{
	@InjectMocks
	AppUserDetailsService userDetailsService;
	
	@Mock
	UserRepository userRepository;
     
    @Test
    public void testLoadUserByUsername()
    {
        User userOne = new User();
        userOne.setUserName("student");
        userOne.setPassword("pass");
        Role role = new Role();
        role.setRoleName("STUDENT_USER");
        Set<Role> roleSet = new HashSet<>(Arrays.asList(role));
        userOne.setRoles(roleSet);
        
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        UserDetails user = userDetailsService.loadUserByUsername("test");
        assertEquals("student", user.getUsername());
    }
    
   
}