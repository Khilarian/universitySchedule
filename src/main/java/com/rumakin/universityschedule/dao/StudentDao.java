package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Student;

@Repository
public interface StudentDao extends CrudRepository<Student, Integer> {

    public Optional<Student> findByEmail(String email);

    public Optional<Student> findByPhone(String phone);
}
