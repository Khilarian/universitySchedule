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
    public List<UserDto> findAll() {
        logger.debug("findAll() users");
        List<UserDto> users = userService.findAll().stream().map(b -> convertToDto(b)).collect(Collectors.toList());
        logger.debug("found() {} users", users.size());
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") int id) {
        logger.debug("findById() user");
        UserDto userDto = convertToDto(userService.findById(id));
        logger.debug("found() {} user", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable(value = "email") String email) {
        logger.debug("findByEmail() user");
        UserDto userDto = convertToDto(userService.findByEmail(email));
        logger.debug("found() {} user", userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> add(@Valid @RequestBody UserDto userDto) {
        User user = userService.add(convertToEntity(userDto));
        return new ResponseEntity<>(convertToDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        User user = userService.update(convertToEntity(userDto));
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updatePassword(@Valid @RequestBody UserDto userDto, String newPassword) {
        userService.updatePassword(convertToEntity(userDto), newPassword);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteById(@PathVariable(value = "id") int id) {
        userService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/markAsDeleted/{id}")
    public ResponseEntity<UserDto> markAsDeleteById(@PathVariable(value = "id") int id) {
        userService.markAsDeleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDto> deleteByEmail(@PathVariable(value = "email") String email) {
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
