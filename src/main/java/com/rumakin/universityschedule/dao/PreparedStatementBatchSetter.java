package com.rumakin.universityschedule.dao;

import java.sql.*;

public interface PreparedStatementBatchSetter<T> {

    void setStatements(PreparedStatement ps, T t) throws SQLException;

}
