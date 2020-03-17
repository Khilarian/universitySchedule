package com.rumakin.universityschedule.dao.addbatch;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.rumakin.universityschedule.models.Person;

public class PersonAddBatch implements BatchPreparedStatementSetter {

    private final List<Person> people;

    public PersonAddBatch(final List<Person> data) {
        this.people = data;
    }

    public final void setValues(final PreparedStatement ps, final int i) throws SQLException {
        String firstName = people.get(i).getFirstName();
        String lastName = people.get(i).getLastName();
        ps.setString(1, firstName);
        ps.setString(2, lastName);
    }

    @Override
    public int getBatchSize() {
        return people.size();
    }
}
