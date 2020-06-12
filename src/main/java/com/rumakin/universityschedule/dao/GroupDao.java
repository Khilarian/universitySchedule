package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Group;

@Repository
public interface GroupDao extends CrudRepository<Group, Integer> {

    public Group findByName(String name);
}
