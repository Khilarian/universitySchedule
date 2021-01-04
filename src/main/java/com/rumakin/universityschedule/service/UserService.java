package com.rumakin.universityschedule.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.dao.UserDao;
import com.rumakin.universityschedule.exception.*;
import com.rumakin.universityschedule.model.*;
import com.rumakin.universityschedule.model.enums.Role;
import com.rumakin.universityschedule.model.enums.Status;
import com.rumakin.universityschedule.utils.MailSender;
import com.rumakin.universityschedule.utils.PasswordGenerator;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public List<User> findAll() {
        logger.debug("findAll() users.");
        List<User> users = (List<User>) userDao.findAll();
        logger.trace("findAll() result: {} users.", users.size());
        return users;
    }

    public User findById(int id) {
        logger.debug("findById() id {}.", id);
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d not found.", id)));
        logger.trace("findById() {} result: {}.", id, user);
        return user;
    }

    public User findByEmail(String email) {
        logger.debug("findByEmail() {}.", email);
        User user = userDao.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with email %s not found.", email)));
        logger.trace("findByName() {} result: {}.", email, user);
        return user;
    }

    public User add(User user) {
        logger.debug("add() {}.", user);
        if (user.getId() != 0) {
            logger.warn("add() fault: user {} was not added, with incorrect id {}.", user, user.getId());
            throw new InvalidEntityException("Id must be 0 for create.");
        }
        String password = user.getPassword();
        if (password == null) {
            password = PasswordGenerator.generatePassword();
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
        }
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        if (user.getStatus() == null) {
            user.setStatus(Status.ACTIVE);
        }
        userDao.save(user);

        mailSender.sendRegistrationMail(new User(user.getFirstName(), user.getLastName(), user.getEmail(), password));

        logger.trace("user {} was added.", user);
        return user;
    }

    public User update(User user) {
        logger.debug("update() {}.", user);
        if (user.getId() == 0) {
            logger.warn("update() fault: user {} was not updated, with incorrect id {}.", user, user.getId());
            throw new InvalidEntityException("Id must be greater than 0 to update.");
        }
        if (user.getPassword() == null) {
            user.setPassword(findByEmail(user.getEmail()).getPassword());
        }
        if (user.getRole() == null) {
            user.setRole(findByEmail(user.getEmail()).getRole());
        }
        if (user.getStatus() == null) {
            user.setStatus(findByEmail(user.getEmail()).getStatus());
        }
        user = userDao.save(user);
        logger.trace("user {} was updated.", user);
        return user;
    }

    public void deleteById(int id) {
        logger.debug("deleteById() id {}.", id);
        userDao.deleteById(id);
    }

    @Transactional
    public void deleteByEmail(String email) {
        logger.debug("deleteByEmail() email {}.", email);
        userDao.deleteByEmail(email);
    }

}
