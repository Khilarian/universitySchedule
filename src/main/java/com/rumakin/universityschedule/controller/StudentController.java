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

import com.rumakin.universityschedule.dto.StudentDto;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String ALL = "All students";
    private static final String EDIT = "Edit student";
    private static final String ADD = "Add student";
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
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() students");
        List<StudentDto> students = studentService.findAll().stream().map(a -> convertToDto(a))
                .collect(Collectors.toList());
        logger.trace("found {} students.", students.size());
        model.addAttribute("students", students);
        setAttributes(model, ALL);
        return "students/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        StudentDto studentDto = new StudentDto();
        if (id != null) {
            studentDto = convertToDto(studentService.findById(id));
            logger.trace("GET edit() found: {}", studentDto);
        }
        model.addAttribute("student", studentDto);
        setEdit(id, model);
        return "students/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "student") StudentDto studentDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", studentDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(studentDto.getId(), model);
            return "students/edit";
        } else {
            if (studentDto.getId() == null) {
                studentService.add(convertToEntity(studentDto));
            } else {
                studentService.update(convertToEntity(studentDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        studentService.deleteById(id);
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
        model.addAttribute("groups", studentService.getGroups());
    }

    private StudentDto convertToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }

}
