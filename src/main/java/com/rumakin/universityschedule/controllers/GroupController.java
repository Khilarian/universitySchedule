package com.rumakin.universityschedule.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/find")
    @ResponseBody
    public GroupDto find(int id) {
        GroupDto groupDto = groupService.find(id);
        return groupDto;
    }

//    @GetMapping("/add")
//    public void createForm(Model model ) {
//        model.addAttribute("faculties", groupService.getFaculties());
//        model.addAttribute("groupDto", new GroupDto());
//        //return REDIRECT_PAGE;
//    }
//    
//    @PostMapping("/add")
//    public String add(GroupDto groupDto) { 
//        System.err.println(groupDto);
//        groupService.add(groupDto);
//        return REDIRECT_PAGE;
//    }

    @RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.GET })
    public String add(Model model, @ModelAttribute("groupDto") GroupDto groupDto) {
        model.addAttribute("groupDto", new GroupDto());
        model.addAttribute("faculties", groupService.getFaculties());
        System.err.println(groupDto);
        groupService.add(groupDto);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.GET })
    public String update(GroupDto groupDto) {
        // GroupDto groupDto = new GroupDto(id, name, facultyId);
        groupService.update(groupDto);
        return REDIRECT_PAGE;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String delete(int id) {
        groupService.delete(id);
        return REDIRECT_PAGE;
    }
}
