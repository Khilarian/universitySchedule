package com.rumakin.universityschedule.controllers;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private static final String REDIRECT_PAGE = "redirect:/groups/getAll";

    private final GroupService groupService;
    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/getAll")
    public String findAllGroups(Model model) {
        logger.debug("findAll() groupDtos");
        List<GroupDto> groupDtos = groupService.findAll();
        logger.trace("found {} groups.", groupDtos.size());
        model.addAttribute("groups", groupDtos);
        List<Faculty> faculties = groupService.getFaculties();
        model.addAttribute("faculties", faculties);
        return "groups/getAll";
    }

    @GetMapping("/add")
    public void createForm(Model model) {
        model.addAttribute("faculties", groupService.getFaculties());
        model.addAttribute("group", new GroupDto());
    }

    @PostMapping("/add")
    public String add(GroupDto groupDto) {
        groupService.add(groupDto);
        return REDIRECT_PAGE;
    }

    @GetMapping("/edit")
    public void edit(int id, Model model) {
        GroupDto group = groupService.find(id);
        if (group == null) throw new ResourceNotFoundException();
        model.addAttribute("faculties", groupService.getFaculties());
        model.addAttribute("group", group);
        //return "groups/edit";
    }

    @PostMapping("/edit")
    public String edit(GroupDto group) {
        groupService.update(group);
        return REDIRECT_PAGE;
    }

    @GetMapping("/delete")
    public String deleteUser( int id) {
        groupService.delete(id);
        return REDIRECT_PAGE;
    }
}
