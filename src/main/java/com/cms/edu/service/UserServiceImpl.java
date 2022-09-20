package com.cms.edu.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.edu.domain.Role;
import com.cms.edu.domain.User;
import com.cms.edu.exception.EntityNotFoundException;
import com.cms.edu.repository.RoleRepository;
import com.cms.edu.repository.UserRepository;

/**
 * 
 */
@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByUserName(username);
    }

    /*
     * find all users
     */
    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }
    
    /*
     * save the user
     */
    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }
    
    /*
     * updated the user
     */
    @Override
    public User update(final long id , final User user) {
    	Optional<User> userData = userRepository.findById(id);
    	if(userData.isPresent()) {
    		User usr = userData.get();
    		usr.setEmailId(user.getEmailId());
    		usr.setFirstName(user.getFirstName());
    		usr.setLastName(user.getLastName());
    		usr.setPassword(user.getPassword());;
    		usr.setPhoneNumber(user.getPhoneNumber());
    		usr.setRoles(user.getRoles());
    		usr.setUserName(user.getUserName());
    		return userRepository.save(usr);
    	}else {
			throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		}
    }
    
    /*
     * delete role
     */
    @Override
    public void delete(final long id) {
    	try {
    		userRepository.deleteById(id);
    	}catch(Exception e) {
    		throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
    	}
    }
    
    /*
     * find all users by role id
     */
    @Override
    public Set<User> findAllUsersByRoleId(final long id) {
    	
    	Optional<Role> roleData = roleRepository.findById(id);

    	if(!roleData.isPresent()) {
    		throw new EntityNotFoundException(Role.class, "id", String.valueOf(id));
    	}
    	
    	return roleData.get().getUsers();
    }
}