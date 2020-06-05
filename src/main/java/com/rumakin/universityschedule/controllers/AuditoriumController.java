package com.rumakin.universityschedule.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.models.*;
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
        List<AuditoriumDto> auditoriums = auditoriumService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} auditoriums.", auditoriums.size());
        model.addAttribute("auditoriums", auditoriums);
        List<Building> buildings = auditoriumService.getBuildings();
        model.addAttribute("buildings", buildings);
        return "auditoriums/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public AuditoriumDto find(int id) {
        return convertToDto(auditoriumService.find(id));
    }

    @PostMapping("/add")
    public String add(@Valid @RequestBody AuditoriumDto auditoriumDto) {
        auditoriumService.add(convertToEntity(auditoriumDto));
        return REDIRECT_PAGE;
    }

    @PostMapping(value = "/update")
    public String update(@Valid @RequestBody AuditoriumDto auditoriumDto) {
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
