package com.rumakin.universityschedule.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface Dao<T> {

    void addAll(List<T> data);

    void add(T entity);

    <E> T find(E id);

    List<T> findAll();

    RowMapper<T> mapRow();

    <E> void remove(E id);
}
