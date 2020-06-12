package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Integer> {
}
