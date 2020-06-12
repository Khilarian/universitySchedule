package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Person;

@Repository
public interface PersonDao extends Dao<Person, Integer> {
}
