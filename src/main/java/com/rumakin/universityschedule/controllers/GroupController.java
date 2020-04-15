package com.rumakin.universityschedule.controllers;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    // maybe @Autowired here will be better than with constructor?
    private final GroupService groupService;
    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/getAll")
    public String findAllGroups(Model model) {
        logger.debug("findAll() groups");
        List<Group> groups = groupService.findAll();
        logger.trace("found {} groups.", groups.size());
        model.addAttribute("groups", groups);
        return "groups/getAll";
    }

}
