package com.rumakin.universityschedule.dao;

import java.util.List;

public interface Dao<T> {

    void addAll(List<T> data);

    void add(T entity);

    T findById(int id);

    List<T> findAll();

    // void delete(T entity);
}
