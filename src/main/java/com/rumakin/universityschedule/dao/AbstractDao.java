package com.rumakin.universityschedule.dao;

public abstract class AbstractDao {

    protected static final String ADD = "INSERT INTO %s %s (%s) VALUES (%s) RETURNING %s;";
    protected static final String FIND_ALL = "SELECT * FROM %s;";
    protected static final String FIND_BY_ID = "SELECT * FROM %s WHERE %s=?;";
    protected static final String REMOVE_BY_ID = "DELETE FROM %s WHERE %s=?;";

    protected AbstractDao() {
    }

    protected static String inputFieldPrepare(int size) {
        StringBuilder builder = new StringBuilder("?");
        for (int i = 1; i < size; i++) {
            builder.append(",")
                    .append("?");
        }
        return builder.toString();
    }

}
