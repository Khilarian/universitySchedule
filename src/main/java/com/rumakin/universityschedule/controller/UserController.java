package com.rumakin.universityschedule.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.rumakin.universityschedule.dto.*;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.model.enums.Role;
import com.rumakin.universityschedule.model.enums.Status;
import com.rumakin.universityschedule.service.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String ALL = "All users";
    private static final String EDIT = "Edit user";
    private static final String ADD = "Add user";
    private static final String PROFILE = "User profile";
    private static final String REDIRECT_PAGE = "redirect:/users/getAll";

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('write')")
    public String findAll(Model model) {
        logger.debug("findAll() users");
        List<UserDto> users = userService.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        logger.trace("found {} users.", users.size());
        model.addAttribute("users", users);
        setAttributes(model, ALL);
        return "users/getAll";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(Integer id, Model model) {
        logger.debug("GET edit() id {}", id);
        if (id == null) {
            throw new UnsupportedOperationException("Can't add user from UI");
        }
        UserDto userDto = convertToDto(userService.findById(id));
        logger.trace("GET edit() found: {}", userDto);
        model.addAttribute("user", userDto);

        setEdit(id, model);
        return "users/edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('write')")
    public String edit(@Valid @ModelAttribute(value = "user") UserDto userDto, BindingResult bindingResult,
            Model model) {
        logger.debug("POST edit() {},{}", userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            setEdit(userDto.getId(), model);
            return "users/edit";
        } else {
            if (userDto.getId() == null) {
                throw new UnsupportedOperationException("Can't add user from UI");
            } else {
                userService.update(convertToEntity(userDto));
            }
            return REDIRECT_PAGE;
        }
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('write')")
    public String delete(int id) {
        logger.debug("GET delete() id {}", id);
        userService.markAsDeleteById(id);
        return REDIRECT_PAGE;
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("hasAuthority('read')")
    public String getProfile(Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", convertToDto(user));
        setAttributes(model, PROFILE);
        return "users/profile";
    }

    @PostMapping(value = "/changePassword")
    @PreAuthorize("hasAuthority('read')")
    public String updatePassword(@Valid @ModelAttribute(value = "user") UserDto userDto, Authentication authentication,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || userDto.getNewPassword().length() == 0) {
            model.addAttribute("passwordError", true);
            setAttributes(model, PROFILE);
        } else {
            model.addAttribute("passwordSuccess", true);
            userService.updatePassword(convertToEntity(userDto), userDto.getNewPassword());
            model.addAttribute("user", convertToDto(userService.findByEmail(authentication.getName())));
        }
        return "users/profile";
    }

    private void setEdit(Integer id, Model model) {
        logger.debug("setEdit() id {}", id);
        if (id != null) {
            setAttributes(model, EDIT);
        } else {
            setAttributes(model, ADD);
        }
        model.addAttribute("roles", Arrays.asList(Role.values()));
        model.addAttribute("statuses", Arrays.asList(Status.values()));
    }

    private void setAttributes(Model model, String header) {
        model.addAttribute("headerString", header);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole().name());
        userDto.setStatus(user.getStatus().name());
        return userDto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setStatus(Status.valueOf(userDto.getStatus()));
        return user;
    }

}
