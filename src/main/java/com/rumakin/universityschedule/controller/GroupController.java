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

    private static final String ALL = "All groups";
    private static final String EDIT = "Edit groupum";
    private static final String ADD = "Add groupum";
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
    @PreAuthorize("hasAuthority('write')")
    public String findAllGroups(Model model) {
        logger.debug("findAll() groupDtos");
        List<GroupDto> groups = groupService.findAll().stream().map(g -> convertToDto(g)).collect(Collectors.toList());
        logger.trace("found {} groups.", groups.size());
        model.addAttribute("groups", groups);
        setAttributes(model, ALL);
        return "groups/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        GroupDto groupDto = new GroupDto();
        if (id != null) {
            groupDto = convertToDto(groupService.findById(id));
            logger.trace("GET edit() found: {}", groupDto);
        }
        model.addAttribute("group", groupDto);
        setEdit(id, model);
        return "groups/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "group") GroupDto groupDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", groupDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(groupDto.getId(), model);
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
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(int id) {
        logger.debug("GET delete() id {}", id);
        groupService.deleteById(id);
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
        model.addAttribute("faculties", groupService.getFaculties());
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }
}
