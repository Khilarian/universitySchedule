package com.rumakin.universityschedule.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.AuditoriumDto;
import com.rumakin.universityschedule.model.*;
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

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        AuditoriumDto auditorium = new AuditoriumDto();
        if (id != null) {
            auditorium = convertToDto(auditoriumService.findById(id));
            model.addAttribute("headerString", "Edit auditorium");
        } else {
            model.addAttribute("headerString", "Add auditorium");
        }
        model.addAttribute("buildings", auditoriumService.getBuildings());
        model.addAttribute("auditorium", auditorium);
        return "auditoriums/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "auditorium") AuditoriumDto auditoriumDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Building> buildings = auditoriumService.getBuildings();
            model.addAttribute("buildings", buildings);
            return "auditoriums/edit";
        } else {
            if (auditoriumDto.getId() == null) {
                auditoriumService.add(convertToEntity(auditoriumDto));
            } else {
                auditoriumService.update(convertToEntity(auditoriumDto));
            }
            return REDIRECT_PAGE;
        }
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
