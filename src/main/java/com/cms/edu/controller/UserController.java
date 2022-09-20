package com.cms.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cms.edu.domain.User;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.service.UserService;

import java.util.List;
import java.util.Set;

/**
 * 
 */
@RestController
@RequestMapping("/cms")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public List<User> listUsers(){
        return userService.findAllUsers();
    }
    
    @RequestMapping(value ="/role/{roleId}/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public Set<User> listUsersByRoleId(@PathVariable("roleId") long roleId) throws EntityNotFoundException{
    	return userService.findAllUsersByRoleId(roleId);
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.POST,produces="application/json")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public User createUser(@RequestBody User user) {
      return userService.save(user);
    }
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT,produces="application/json")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public User updateUser(@PathVariable("id") long id, @RequestBody User user)  throws EntityNotFoundException{
      return userService.update(id, user);
    }
    
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE,produces="application/json")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN_USER')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id)  throws EntityNotFoundException{
      userService.delete(id);
      return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}