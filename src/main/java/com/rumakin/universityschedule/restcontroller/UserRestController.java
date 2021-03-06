package com.rumakin.universityschedule.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumakin.universityschedule.dto.UserDto;
import com.rumakin.universityschedule.model.User;
import com.rumakin.universityschedule.service.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/users")
@Api(value = "Users Resourse REST Endpoint")
public class UserRestController {

    private UserService userService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public List<UserDto> findAll() {
        logger.debug("findAll() users");
        List<UserDto> users = userService.findAll().stream().map(this :: convertToDto).collect(Collectors.toList());
        logger.debug("found() {} users", users.size());
        return users;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() user id {}", id);
        UserDto userDto = convertToDto(userService.findById(id));
        logger.debug("found() {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> findByEmail(@PathVariable(value = "email") String email) {
        logger.debug("findByEmail() user email {}", email);
        UserDto userDto = convertToDto(userService.findByEmail(email));
        logger.debug("found() {}", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> add(@Valid @RequestBody UserDto userDto) {
        logger.debug("add() user email {}", userDto);
        User user = userService.add(convertToEntity(userDto));
        return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        logger.debug("update() user email {}", userDto);
        User user = userService.update(convertToEntity(userDto));
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> updatePassword(@Valid @RequestBody UserDto userDto, String newPassword) {
        logger.debug("updatePassword() user {}, password {}", userDto, newPassword);
        userService.updatePassword(convertToEntity(userDto), newPassword);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> deleteById(@PathVariable(value = "id") int id) {
        logger.debug("deleteById() user id {}", id);
        userService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/markAsDeleted/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> markAsDeleteById(@PathVariable(value = "id") int id) {
        logger.debug("markAsDeleteById() user id {}", id);
        userService.markAsDeleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserDto> deleteByEmail(@PathVariable(value = "email") String email) {
        logger.debug("deleteByEmail() user email {}", email);
        userService.deleteByEmail(email);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}
