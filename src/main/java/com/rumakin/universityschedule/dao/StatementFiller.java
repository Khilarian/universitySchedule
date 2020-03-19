package com.rumakin.universityschedule.dao;

import java.sql.*;

public interface StatementFiller<T> {

    void setParameters(PreparedStatement ps, T t) throws SQLException;

}
