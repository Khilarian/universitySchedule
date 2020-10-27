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

import com.rumakin.universityschedule.dto.CourseDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

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
    public String findAllCourses(Model model) {
        logger.debug("findAll() courseDtos");
        List<CourseDto> courses = courseService.findAll().stream().map(g -> convertToDto(g))
                .collect(Collectors.toList());
        logger.trace("found {} courses.", courses.size());
        model.addAttribute("courses", courses);
        List<Faculty> faculties = courseService.getFaculties();
        model.addAttribute("faculties", faculties);
        return "courses/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        CourseDto course = new CourseDto();
        if (id != null) {
            course = convertToDto(courseService.findById(id));
            model.addAttribute("headerString", "Edit course");
        } else {
            model.addAttribute("headerString", "Add course");
        }
        model.addAttribute("faculties", courseService.getFaculties());
        model.addAttribute("course", course);
        return "courses/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "course") CourseDto courseDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", courseService.getFaculties());
            return "courses/edit";
        } else {
            Course course = convertToEntity(courseDto);
            if (course.getFaculty().getId() == 0) {
                course.setFaculty(null);
            }
            if (courseDto.getId() == 0) {
                courseService.add(course);
            } else {
                courseService.update(course);
            }
            return REDIRECT_PAGE;
        }

    }

    @GetMapping("/delete")
    public String deleteUser(int id) {
        courseService.delete(id);
        return REDIRECT_PAGE;
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}
