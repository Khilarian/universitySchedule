package com.rumakin.universityschedule.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rumakin.universityschedule.model.User;

@Service
public class MailSender {

    private static final String SUBJECT = "Registration on University Platfofm";

    @Value("${spring.mail.username}")
    private String username;

    private JavaMailSender javaMailSender;

    @Autowired
    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationMail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(prepareMessage(user));

        javaMailSender.send(simpleMailMessage);
    }

    private String prepareMessage(User user) {
        String messageForm = "Hello, %s %s!\n You was registered on our university platform!\n "
                + "Your login is: %s\n Your password is:%s\n Web site is: http://localhost:8080/university";
        return String.format(messageForm, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

}
