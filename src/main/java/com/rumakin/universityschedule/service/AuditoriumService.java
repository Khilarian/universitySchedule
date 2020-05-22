package com.rumakin.universityschedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.models.*;

@Service
public class AuditoriumService {

    private AuditoriumDao auditoriumDao;
    private BuildingService buildingService;
    private Logger logger = LoggerFactory.getLogger(AuditoriumService.class);

    @Autowired
    public AuditoriumService(AuditoriumDao auditoriumDao, BuildingService buildingService) {
        this.auditoriumDao = auditoriumDao;
        this.buildingService=buildingService;
    }

    public List<AuditoriumDto> findAll() {
        logger.debug("findAll() auditoriums");
        List<Auditorium> auditoriums = (List<Auditorium>) auditoriumDao.findAll();
        logger.trace("found {} auditoriums", auditoriums.size());
        return auditoriums.stream().map(a-> new AuditoriumDto(a)).collect(Collectors.toList());
    }

    public AuditoriumDto find(int id) {
        logger.debug("find() id {}", id);
        Auditorium auditorium = auditoriumDao.findById(id).get();
        logger.trace("found {}", auditorium);
        return new AuditoriumDto(auditorium);
    }

    public Auditorium add(AuditoriumDto auditoriumDto) {
        logger.debug("add() {}", auditoriumDto);
        Building building = buildingService.find(auditoriumDto.getBuildingId());
        Auditorium auditorium = new Auditorium(auditoriumDto.getNumber(),auditoriumDto.getCapacity(),building);
        return auditoriumDao.save(auditorium);
    }

    public void update(AuditoriumDto auditoriumDto) {
        logger.debug("update() {}.", auditoriumDto);
        Building building = buildingService.find(auditoriumDto.getBuildingId());
        Auditorium auditorium = new Auditorium(auditoriumDto.getId(), auditoriumDto.getNumber(),auditoriumDto.getCapacity(),building);
        auditoriumDao.save(auditorium);
        logger.trace("auditorium {} was updated.", auditorium);
    }

    public void delete(int id) {
        logger.debug("delete() id {}.", id);
        auditoriumDao.deleteById(id);
    }
    
    public List<Building> getBuildings(){
        return buildingService.findAll();
    }

}
