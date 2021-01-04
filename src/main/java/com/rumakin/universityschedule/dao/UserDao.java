package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

}
