package com.rumakin.universityschedule.controllers;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.exceptions.ResourceNotFoundException;
import com.rumakin.universityschedule.models.Faculty;
import com.rumakin.universityschedule.models.Group;
import com.rumakin.universityschedule.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private static final String REDIRECT_PAGE = "redirect:/groups/getAll";

    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
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

    @PostMapping("/add")
    public String add(GroupDto groupDto) {
        groupService.add(groupDto);
        return REDIRECT_PAGE;
    }

    @GetMapping("/find")
    @ResponseBody
    public GroupDto find(int id) {
        GroupDto groupDto = groupService.find(id);
        if (groupDto == null) {
            throw new ResourceNotFoundException();
        }
        return groupDto;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(GroupDto groupDto) {
        groupService.update(groupDto);
        return REDIRECT_PAGE;
    }

    @GetMapping("/delete")
    public String deleteUser(int id) {
        groupService.delete(id);
        return REDIRECT_PAGE;
    }
    
    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }
    
    
}
