package com.rumakin.universityschedule.dao;

import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.*;

@Repository
public interface StudentDao extends Dao<Student, Integer> {
}
