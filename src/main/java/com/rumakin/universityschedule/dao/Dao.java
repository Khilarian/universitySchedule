package com.rumakin.universityschedule.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface Dao<T> {

    void addAll(List<T> data);

    void add(T entity);

    T findById(int id);

    List<T> findAll();

    RowMapper<T> mapRow();

    // void delete(T entity);
}
