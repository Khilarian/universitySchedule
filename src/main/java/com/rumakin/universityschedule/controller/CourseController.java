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

import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private static final String ALL = "All courses";
    private static final String EDIT = "Edit course";
    private static final String ADD = "Add course";
    private static final String REDIRECT_PAGE = "redirect:/courses/getAll";

    private final CourseService courseService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAllCourses(Model model) {
        logger.debug("findAll() courseDtos");
        List<CourseDto> courses = courseService.findAll().stream().map(g -> convertToDto(g))
                .collect(Collectors.toList());
        logger.trace("found {} courses.", courses.size());
        model.addAttribute("courses", courses);
        setAttributes(model, ALL);
        return "courses/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        CourseDto courseDto = new CourseDto();
        if (id != null) {
            courseDto = convertToDto(courseService.findById(id));
            logger.trace("GET edit() found: {}", courseDto);
        }
        model.addAttribute("course", courseDto);
        setEdit(id, model);
        return "courses/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "course") CourseDto courseDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", courseDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(courseDto.getId(), model);
            return "courses/edit";
        } else {
            Course course = convertToEntity(courseDto);
            if (courseDto.getId() == null) {
                courseService.add(course);
            } else {
                courseService.update(course);
            }
            return REDIRECT_PAGE;
        }

    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(int id) {
        logger.debug("GET delete() id {}", id);
        courseService.deleteById(id);
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
        model.addAttribute("faculties", courseService.getFaculties());
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}
