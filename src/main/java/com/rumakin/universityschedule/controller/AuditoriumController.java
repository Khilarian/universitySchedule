package com.rumakin.universityschedule.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final String ALL = "All auditoriums";
    private static final String EDIT = "Edit auditorium";
    private static final String ADD = "Add auditorium";
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
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() auditoriums");
        List<AuditoriumDto> auditoriums = auditoriumService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} auditoriums.", auditoriums.size());
        model.addAttribute("auditoriums", auditoriums);
        setAttributes(model, ALL);
        return "auditoriums/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        AuditoriumDto auditoriumDto = new AuditoriumDto();
        if (id != null) {
            auditoriumDto = convertToDto(auditoriumService.findById(id));
            logger.trace("GET edit() found: {}", auditoriumDto);
        }
        model.addAttribute("auditorium", auditoriumDto);
        setEdit(id, model);
        return "auditoriums/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "auditorium") AuditoriumDto auditoriumDto,
            BindingResult bindingResult, Model model) {
        logger.debug("POST edit() {},{}", auditoriumDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(auditoriumDto.getId(), model);
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
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        auditoriumService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private void setEdit(Integer id, Model model) {
        logger.debug("setEdit() id {}", id);
        if (id != null) {
            setAttributes(model, EDIT);
        } else {
            setAttributes(model, ADD);
        }
    }

    private void setAttributes(Model model, String header) {
        model.addAttribute("headerString", header);
        model.addAttribute("buildings", auditoriumService.getBuildings());
    }

    private AuditoriumDto convertToDto(Auditorium auditorium) {
        return modelMapper.map(auditorium, AuditoriumDto.class);
    }

    private Auditorium convertToEntity(AuditoriumDto auditoriumDto) {
        return modelMapper.map(auditoriumDto, Auditorium.class);
    }

}
