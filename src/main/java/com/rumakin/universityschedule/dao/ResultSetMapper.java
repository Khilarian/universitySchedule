package com.rumakin.universityschedule.dao;

import java.sql.*;

public interface ResultSetMapper<T> {

    void setParameters(PreparedStatement ps, T t) throws SQLException;

}
