package com.cms.edu.service;

import java.util.List;
import java.util.Set;

import com.cms.edu.domain.User;

/**
 * 
 */
public interface UserService {
	public User findByUsername(final String username);

    public List<User> findAllUsers();
    
    public Set<User> findAllUsersByRoleId(final long id);
    
    public User save(final User user);
    
    public User update(final long id , final User user);
    
    public void delete(final long id);
    
}