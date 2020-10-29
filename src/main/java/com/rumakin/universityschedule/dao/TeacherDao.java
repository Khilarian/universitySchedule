package com.rumakin.universityschedule.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Teacher;

@Repository
public interface TeacherDao extends CrudRepository<Teacher, Integer> {

    public Optional<Teacher> findByEmail(String email);

    public Optional<Teacher> findByPhone(String phone);

}
