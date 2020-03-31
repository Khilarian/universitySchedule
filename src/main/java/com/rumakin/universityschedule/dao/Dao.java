package com.rumakin.universityschedule.dao;

import java.sql.*;
import java.util.*;

import org.springframework.jdbc.core.RowMapper;

public interface Dao<T> {

    List<T> addAll(List<T> data);

    T add(T entity);

    <E> T find(E id);

    List<T> findAll();

    RowMapper<T> mapRow();

    <E> void remove(E id);

    //void setParameters(PreparedStatement ps, T t) throws SQLException;

<<<<<<< HEAD
     String getFieldsList(String alias);
=======
    // String getFieldsList(String alias);
>>>>>>> 03f0f99477e04ee4dd233cc9468ca2d78120090c

    default String addAlias(String alias, String text) {
        return alias + "." + text;
    }

    default String formatFieldsList(String alias, List<String> fields) {
        return fields.stream().map(a -> addAlias(alias, a)).reduce((a, b) -> a + ',' + b).orElse("empty list");
    }
}
