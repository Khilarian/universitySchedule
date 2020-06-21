package com.rumakin.universityschedule.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumakin.universityschedule.model.Auditorium;

@Repository
public interface AuditoriumDao extends CrudRepository<Auditorium, Integer> {

    public Auditorium findByNumberAndBuildingId(int number, int buildingId);
}
