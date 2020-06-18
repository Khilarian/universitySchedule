package com.rumakin.universityschedule.restcontroller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.GroupDto;
import com.rumakin.universityschedule.model.Group;
import com.rumakin.universityschedule.service.GroupService;

@RestController
@Validated
@RequestMapping("/api/groups")
public class GroupRestController {

    private GroupService groupService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(GroupRestController.class);

    @Autowired
    public GroupRestController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    public List<GroupDto> findAll() {
        logger.debug("findAll() groups");
        List<GroupDto> groups = groupService.findAll().stream().map(b -> convertToDto(b))
                .collect(Collectors.toList());
        logger.debug("found() {} groups", groups.size());
        return groups;
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<GroupDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("find() group");
        GroupDto groupDto = convertToDto(groupService.findById(id));
        logger.debug("found() {} group", groupDto);
        return new ResponseEntity<>(groupDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<GroupDto> add(@Valid @RequestBody GroupDto groupDto) {
        Group group = groupService.add(convertToEntity(groupDto));
        return new ResponseEntity<>(convertToDto(group), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<GroupDto> update(@Valid @RequestBody GroupDto groupDto) {
        Group group = groupService.update(convertToEntity(groupDto));
        return new ResponseEntity<>(convertToDto(group), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<GroupDto> delete(@PathVariable(value = "id") int id) {
        groupService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    private Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }

}
