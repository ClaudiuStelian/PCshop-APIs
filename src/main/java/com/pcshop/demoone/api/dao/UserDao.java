package com.pcshop.demoone.api.dao;

import org.springframework.data.repository.CrudRepository;

import com.pcshop.demoone.api.model.User;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByName(String name);

    User findByEmail(String email);

}
