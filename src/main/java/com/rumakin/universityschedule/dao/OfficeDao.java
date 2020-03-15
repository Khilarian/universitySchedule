package com.rumakin.universityschedule.dao;

import java.util.List;

import com.rumakin.universityschedule.models.Office;

public class OfficeDao implements Dao<Office> {

    private static final String TABLE = "office as o";
    private static final String OPENED_FROM = "opened_from";
    private static final String OPENED_TILL = "opened_till";
    private static final String ROOM_TABLE_NAME = "room as r";
    private static final String ROOM_ID = "room_id";
    private static final String ROOM_NUMBER = "room_number";
    private static final String BUILDING_TABLE_NAME = "building as b";
    private static final String BUILDING_ID = "building_id";
    private static final String BUILDING_NAME = "building_name";
    private static final String BUILDING_ADDRESS = "building_addredd";

    @Override
    public void addAll(List<Office> data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(Office entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public Office findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Office> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

}
