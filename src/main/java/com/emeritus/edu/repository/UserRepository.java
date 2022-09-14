package com.emeritus.edu.repository;

import org.springframework.data.repository.CrudRepository;

import com.emeritus.edu.domain.User;

/**
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}