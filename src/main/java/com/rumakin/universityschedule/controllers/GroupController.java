package com.rumakin.universityschedule.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.models.*;
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
        List<GroupDto> groups = groupService.findAll().stream().map(g -> convertToDto(g)).collect(Collectors.toList());
        logger.trace("found {} groups.", groups.size());
        model.addAttribute("groups", groups);
        List<Faculty> faculties = groupService.getFaculties();
        model.addAttribute("faculties", faculties);
        return "groups/getAll";
    }

    @PostMapping("/add")
    public String add(GroupDto groupDto) {
        groupService.add(convertToEntity(groupDto));
        return REDIRECT_PAGE;
    }

    @GetMapping("/find")
    @ResponseBody
    public GroupDto find(int id) {
        return convertToDto(groupService.find(id));
    }

    @PostMapping(value = "/update")
    public String update(GroupDto groupDto) {
        groupService.update(convertToEntity(groupDto));
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

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }

}
