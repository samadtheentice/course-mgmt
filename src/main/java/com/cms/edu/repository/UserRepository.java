package com.cms.edu.repository;

import org.springframework.data.repository.CrudRepository;

import com.cms.edu.domain.User;

/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}