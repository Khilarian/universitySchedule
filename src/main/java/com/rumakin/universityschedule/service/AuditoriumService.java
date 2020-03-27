package com.rumakin.universityschedule.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.dao.AuditoriumDao;
import com.rumakin.universityschedule.models.Auditorium;

@Service
public class AuditoriumService {

    private final AuditoriumDao auditoriumDao;

    @Autowired
    public AuditoriumService(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }
}
