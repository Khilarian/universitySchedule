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
        String password = PasswordGenerator.generatePassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
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
        User userDb = findByEmail(user.getEmail());
        if (user.getFirstName() != null && !user.getFirstName().equals(userDb.getFirstName())) {
            userDb.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().equals(userDb.getLastName())) {
            userDb.setLastName(user.getLastName());
        }
        if (user.getRole() != null && !user.getRole().equals(userDb.getRole())) {
            userDb.setRole(user.getRole());
        }
        if (user.getStatus() != null && !user.getStatus().equals(userDb.getStatus())) {
            userDb.setStatus(user.getStatus());
        }
        userDao.save(userDb);
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

    public void markAsDeleteById(Integer id) {
        logger.debug("markAsDeleteById() id {}.", id);
        User user = findById(id);
        user.setStatus(Status.DELETED);
        userDao.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        logger.debug("updatePassword() user {}, newPassword {}.", user, newPassword);
        User userDb = findById(user.getId());
        String encodePassword = passwordEncoder.encode(newPassword);
        userDb.setPassword(encodePassword);
        mailSender.sendUpdateMail(new User(user.getFirstName(), user.getLastName(), user.getEmail(), newPassword));
    }

}
