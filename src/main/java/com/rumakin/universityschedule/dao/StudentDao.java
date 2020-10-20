package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Integer> {
    
    public Student findByEmail(String email);
    
    public Student findByPhone(String phone);
}
