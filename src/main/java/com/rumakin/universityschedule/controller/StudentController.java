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

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String REDIRECT_PAGE = "redirect:/students/getAll";

    private final StudentService studentService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public String findAll(Model model) {
        logger.debug("findAll() students");
        List<StudentDto> students = studentService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} students.", students.size());
        model.addAttribute("students", students);
        List<Group> groups = studentService.getGroups();
        model.addAttribute("groups", groups);
        return "students/getAll";
    }

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        StudentDto student = new StudentDto();
        if (id != null) {
            student = convertToDto(studentService.findById(id));
            model.addAttribute("headerString", "Edit student");
        } else {
            model.addAttribute("headerString", "Add student");
        }
        model.addAttribute("groups", studentService.getGroups());
        model.addAttribute("student", student);
        return "students/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "student") StudentDto studentDto, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            List<Group> groups = studentService.getGroups();
            model.addAttribute("groups", groups);
            return "students/edit";
        } else {
            if (studentDto.getId() == 0) {
                studentService.add(convertToEntity(studentDto));
            } else {
                studentService.update(convertToEntity(studentDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    public String delete(int id) {
        studentService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private StudentDto convertToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

}
