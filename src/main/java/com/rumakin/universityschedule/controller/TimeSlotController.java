package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.*;

@Controller
@RequestMapping("/timeSlots")
public class TimeSlotController {

    private static final String ALL = "All time slots";
    private static final String EDIT = "Edit time slot";
    private static final String ADD = "Add time slot";
    private static final String REDIRECT_PAGE = "redirect:/timeSlots/getAll";

    private TimeSlotService timeSlotService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(TimeSlotController.class);

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService, ModelMapper modelMapper) {
        this.timeSlotService = timeSlotService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() timeSlots");
        List<TimeSlotDto> timeSlots = timeSlotService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.trace("found {} timeSlots.", timeSlots.size());
        model.addAttribute("timeSlots", timeSlots);
        setAttributes(model, ALL);
        return "timeSlots/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        TimeSlotDto timeSlot = new TimeSlotDto();
        if (id != null) {
            timeSlot = convertToDto(timeSlotService.findById(id));
        }
        model.addAttribute("timeSlot", timeSlot);
        setEdit(id, model);
        return "timeSlots/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "timeSlot") TimeSlotDto timeSlotDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            setEdit(timeSlotDto.getId(), model);
            return "timeSlots/edit";
        } else {
            if (timeSlotDto.getId() == null) {
                timeSlotService.add(convertToEntity(timeSlotDto));
            } else {
                timeSlotService.update(convertToEntity(timeSlotDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        timeSlotService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private void setEdit(Integer id, Model model) {
        if (id != null) {
            setAttributes(model, EDIT);
        } else {
            setAttributes(model, ADD);
        }
    }

    private void setAttributes(Model model, String header) {
        model.addAttribute("headerString", header);
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }
}
