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

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private static final String ALL = "All teachers";
    private static final String EDIT = "Edit teacher";
    private static final String ADD = "Add teacher";
    private static final String REDIRECT_PAGE = "redirect:/teachers/getAll";

    private final TeacherService teacherService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    public TeacherController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() teachers");
        List<TeacherDto> teachers = teacherService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} teachers.", teachers.size());
        model.addAttribute("teachers", teachers);
        setAttributes(model, ALL);
        return "teachers/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        TeacherDto teacherDto = new TeacherDto();
        if (id != null) {
            teacherDto = convertToDto(teacherService.findById(id));
            logger.trace("GET edit() found: {}", teacherDto);
        }
        model.addAttribute("teacher", teacherDto);
        setEdit(id, model);
        return "teachers/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "teacher") TeacherDto teacherDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", teacherDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(teacherDto.getId(), model);
            return "teachers/edit";
        } else {
            if (teacherDto.getId() == null) {
                teacherService.add(convertToEntity(teacherDto));
            } else {
                teacherService.update(convertToEntity(teacherDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        teacherService.deleteById(id);
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
        model.addAttribute("faculties", teacherService.getFaculties());
        model.addAttribute("allCourses", teacherService.getCourses());
    }

    private TeacherDto convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    private Teacher convertToEntity(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }

}
