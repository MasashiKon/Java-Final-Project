package com.randombattlegenerator.main.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.randombattlegenerator.main.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    
    List<User> findByUsername(String username);
    
}
