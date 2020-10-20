package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String findAll(Model model) {
        logger.debug("findAll() timeSlots");
        List<TimeSlotDto> timeSlots = timeSlotService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.trace("found {} timeSlots.", timeSlots.size());
        model.addAttribute("timeSlots", timeSlots);
        return "timeSlots/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        TimeSlotDto timeSlot = new TimeSlotDto();
        if (id != null) {
            timeSlot = convertToDto(timeSlotService.findById(id));
            model.addAttribute("headerString", "Edit timeSlot");
        } else {
            model.addAttribute("headerString", "Add timeSlot");
        }
        model.addAttribute("timeSlot", timeSlot);
        return "timeSlots/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "timeSlot") TimeSlotDto timeSlotDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "timeSlots/edit";
        } else {
            if (timeSlotDto.getId() == 0) {
                timeSlotService.add(convertToEntity(timeSlotDto));
            } else {
                timeSlotService.update(convertToEntity(timeSlotDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        timeSlotService.delete(id);
        return REDIRECT_PAGE;
    }

    private TimeSlotDto convertToDto(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

    private TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }
}
