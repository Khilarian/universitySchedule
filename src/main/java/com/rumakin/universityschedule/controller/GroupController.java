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

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.model.*;
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

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        GroupDto group = new GroupDto();
        if (id != null) {
            group = convertToDto(groupService.findById(id));
            model.addAttribute("headerString", "Edit group");
        } else {
            model.addAttribute("headerString", "Add group");
        }
        model.addAttribute("faculties", groupService.getFaculties());
        model.addAttribute("group", group);
        return "groups/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "group") GroupDto groupDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", groupService.getFaculties());
            return "groups/edit";
        } else {
            Group group = convertToEntity(groupDto);
            if (groupDto.getId() == null) {
                groupService.add(group);
            } else {
                groupService.update(group);
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(int id) {
        groupService.deleteById(id);
        return REDIRECT_PAGE;
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }
}
