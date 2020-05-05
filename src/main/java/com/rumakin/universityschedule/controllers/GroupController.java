package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.service.FacultyService;
import com.rumakin.universityschedule.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private static final String REDIRECT_PAGE = "redirect:/groups/getAll";

    private final GroupService groupService;
    private final FacultyService facultyService;
    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupService groupService, FacultyService facultyService) {
        this.groupService = groupService;
        this.facultyService = facultyService;
    }

    @GetMapping("/getAll")
    public String findAllGroups(Model model) {
        logger.debug("findAll() groups");
        List<Group> groups = groupService.findAll();
        logger.trace("found {} groups.", groups.size());
        model.addAttribute("groups", groups);
        List<Faculty> faculties = facultyService.findAll();
        model.addAttribute("faculties", faculties);
        return "groups/getAll";
    }

    @GetMapping("/find")
    @ResponseBody
    public GroupDto find(int id) {
        Group group = groupService.find(id);
        GroupDto dto = new GroupDto(group.getId(), group.getName(), group.getFaculty().getId());
        System.out.println(group);
        return dto;
    }

    @PostMapping("/add")
    public String add(String name, int facultyId) {
        // public String add(Model model, @ModelAttribute("groupDto") GroupDTO dto) {
        System.err.println(name + " " + facultyId);
        // Faculty faculty = facultyService.find(dto.getFacultyId());
        // Group group = new Group(dto.getName(), faculty);
        Faculty faculty = facultyService.find(facultyId);
        Group group = new Group(name, faculty);
        groupService.add(group);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(int id, String name, int facultyId, String facultyName) {
        Faculty faculty = new Faculty(facultyId, facultyName);
        Group group = new Group(name, faculty);
        groupService.update(group);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        groupService.delete(id);
        return REDIRECT_PAGE;
    }
}
