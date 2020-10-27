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

import com.rumakin.universityschedule.dto.TeacherDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

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
    public String findAll(Model model) {
        logger.debug("findAll() teachers");
        List<TeacherDto> teachers = teacherService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} teachers.", teachers.size());
        model.addAttribute("teachers", teachers);
        List<Faculty> faculties = teacherService.getFaculties();
        List<Course> courses = teacherService.getCourses();
        model.addAttribute("faculties", faculties);
        model.addAttribute("allCourses", courses);
        return "teachers/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        TeacherDto teacher = new TeacherDto();
        if (id != null) {
            teacher = convertToDto(teacherService.findById(id));
            model.addAttribute("headerString", "Edit teacher");
        } else {
            model.addAttribute("headerString", "Add teacher");
        }
        List<Faculty> faculties = teacherService.getFaculties();
        List<Course> courses = teacherService.getCourses();
        model.addAttribute("faculties", faculties);
        model.addAttribute("allCourses", courses);
        model.addAttribute("teacher", teacher);
        return "teachers/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "teacher") TeacherDto teacherDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            List<Faculty> faculties = teacherService.getFaculties();
            List<Course> courses = teacherService.getCourses();
            model.addAttribute("faculties", faculties);
            model.addAttribute("allCourses", courses);
            return "teachers/edit";
        } else {
            if (teacherDto.getId() == 0) {
                teacherService.add(convertToEntity(teacherDto));
            } else {
                teacherService.update(convertToEntity(teacherDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        teacherService.delete(id);
        return REDIRECT_PAGE;
    }

    private TeacherDto convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    private Teacher convertToEntity(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }

}
