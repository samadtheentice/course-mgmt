package com.emeritus.edu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
 
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

import com.emeritus.edu.domain.Role;
import com.emeritus.edu.domain.User;
import com.emeritus.edu.repository.RoleRepository;
import com.emeritus.edu.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest 
{
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;
      
    @Mock
    RoleRepository roleRepository;
     
    @Test
    public void testFindAllUsers()
    {
        List<User> list = new ArrayList<User>();
        User userOne = new User();
        User userTwo = new User();
        User userThree = new User();
          
        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);
          
        when(userRepository.findAll()).thenReturn(list);
          

        List<User> userList = userService.findAllUsers();
          
        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    public void testFindByUsername()
    {
        User userOne = new User();
        userOne.setUserName("test");
        when(userRepository.findByUserName(ArgumentMatchers.<String>any())).thenReturn(userOne);
        User user = userService.findByUsername("test");
        assertEquals("test", user.getUserName());
    }
    
    @Test
    public void testFindAllUsersByRoleId()
    {
    	Optional<Role> role = Optional.of(new Role());
        User userOne = new User();
        userOne.setUserName("test");
        Set<User> userSet = new HashSet<>(Arrays.asList(userOne));
        role.get().setUsers(userSet);
        when(roleRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(role);
        Set<User> users = userService.findAllUsersByRoleId(1);
        assertEquals(1, users.size());
    }
    
    @Test
    public void testCreateUser()
    {
    	User userOne = new User();
        userOne.setUserName("test");
        userService.save(userOne);
        verify(userRepository, times(1)).save(userOne);
    }
    
    @Test
    public void testUpdateUser()
    {
    	Optional<User> userOne = Optional.of(new User());
        
        when(userRepository.findById(ArgumentMatchers.<Long>any())).thenReturn(userOne);
        
        userService.update(1,userOne.get());
        verify(userRepository, times(1)).save(userOne.get());
    }
    
    @Test
    public void testDeleteUser()
    {
    	User userOne = new User();
        userOne.setUserName("test");
        doNothing().when(userRepository).deleteById(ArgumentMatchers.<Long>any());
        userService.delete(1);
        verify(userRepository, times(1)).deleteById(ArgumentMatchers.<Long>any());
    }
}