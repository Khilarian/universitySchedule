package com.rumakin.universityschedule.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Auditorium;
import com.rumakin.universityschedule.models.Building;
import com.rumakin.universityschedule.service.AuditoriumService;

@Controller
@RequestMapping("/auditoriums")
public class AuditoriumController {

    private static final String REDIRECT_PAGE = "redirect:/auditoriums/getAll";

    private final AuditoriumService auditoriumService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(AuditoriumController.class);

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService, ModelMapper modelMapper) {
        this.auditoriumService = auditoriumService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriumDtos = auditoriumService.findAll().stream().map(a->convertToDto(a)).collect(Collectors.toList());
        logger.trace("found {} auditoriums.", auditoriumDtos.size());
        model.addAttribute("auditoriums", auditoriumDtos);
        List<Building> buildings = auditoriumService.getBuildings();
        model.addAttribute("buildings", buildings);
        return "auditoriums/getAll";
    }
    
    @PostMapping("/add")
    public String add(AuditoriumDto auditoriumDto) {
        auditoriumService.add(convertToEntity(auditoriumDto));
        return REDIRECT_PAGE;
    }

    @GetMapping("/find")
    @ResponseBody
    public AuditoriumDto find(int id) {
        Auditorium auditorium = auditoriumService.find(id);
        if (auditorium == null) {
            logger.warn("id {} not found", id);
            String message = String.format("Auditorium with id %d not found", id);
            throw new ResourceNotFoundException(message);
        }
        return convertToDto(auditorium);
    }

    

    @PostMapping(value = "/update")
    public String update(AuditoriumDto auditoriumDto) {
        auditoriumService.update(convertToEntity(auditoriumDto));
        return REDIRECT_PAGE;
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        auditoriumService.delete(id);
        return REDIRECT_PAGE;
    }
    
    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }
    
    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
