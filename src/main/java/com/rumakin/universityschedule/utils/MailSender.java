package com.rumakin.universityschedule.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.model.User;

@Service
public class MailSender {

    private static final String REGISTRATION = "Registration on University Platfofm";
    private static final String PASSWORD_CHANGE = "Password change on University Platfofm";

    @Value("${spring.mail.username}")
    private String username;

    private JavaMailSender javaMailSender;
    
    private Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Autowired
    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationMail(User user) {
        logger.debug("sendRegistrationMail() for user {}", user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(REGISTRATION);
        simpleMailMessage.setText(prepareRegistrationMessage(user));

        javaMailSender.send(simpleMailMessage);
    }
    
    public void sendUpdateMail(User user) {
        logger.debug("sendUpdateMail() for user {}", user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(PASSWORD_CHANGE);
        simpleMailMessage.setText(prepareUpdateMessage(user));

        javaMailSender.send(simpleMailMessage);
    }

    private String prepareRegistrationMessage(User user) {
        logger.debug("prepareRegistrarionMessage() user {}", user);
        String messageForm = "Hello, %s %s!\n You was registered on our university platform!\n "
                + "Your login is: %s\n Your password is:%s\n Web site is: http://localhost:8080/university";
        return String.format(messageForm, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    private String prepareUpdateMessage(User user) {
        logger.debug("prepareUpdateMessage() user {}", user);
        String messageForm = "Hello, %s %s!\n Your password on our university platform was changed!\n "
                + "Your login is: %s\n Your password is:%s\n Web site is: http://localhost:8080/university";
        return String.format(messageForm, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

}
