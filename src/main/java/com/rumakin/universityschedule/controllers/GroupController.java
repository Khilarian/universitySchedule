package com.rumakin.universityschedule.controllers;

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

    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        GroupDto group = new GroupDto();
        if (id != null) {
            group = convertToDto(groupService.find(id));
        }
        model.addAttribute("faculties", groupService.getFaculties());
        model.addAttribute("group", group);
        return "groups/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute(value = "group") GroupDto groupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "groups/edit";
        } else {
            if (groupDto.getId() == null) {
                groupService.add(convertToEntity(groupDto));
            } else {
                groupService.update(convertToEntity(groupDto));
            }
            return REDIRECT_PAGE;
        }
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
