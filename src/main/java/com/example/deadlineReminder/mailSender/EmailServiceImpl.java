package com.example.deadlineReminder.mailSender;

import com.example.deadlineReminder.repository.user.UserRepository;
import com.example.deadlineReminder.service.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;
    private final TokenService tokenService;
    private final UserRepository userRepository;


    public void sendEmail(String email,String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        String htmlMessage = htmlEmailForToken(token,userRepository.findByEmail(email).get().getFirstName());

        message.setContent(htmlMessage,"text/html");

        helper.setTo(email);
        helper.setSubject("CONFIRM ACCOUNT REGISTRATION");
        javaMailSender.send(message);
    }

    public String htmlEmailForToken(String token,String firstName){
        return "<html>\n" +
                "<body>\n" +
                "\t<h2>Email confirmation account</h2>\n" +
                "\t<p>Dear "+firstName+" ,</p>\n" +
                "\t<p>This is email for confirm your registration account. Please click the link below to confirm your registration.</p><br>\n" +
                "\t<p>This link just have effective in 15 minutes since you register your account</p><br>\n" +
                "\t<a href=\"localhost:8080/confirm?token="+token+"\">CLICK HERE TO CONFIRM YOUR ACCOUNT</a>\n" +
                "\t<br>\n" +
                "\t<p>Thanks for using our product. Have a nice day!</p>\n" +
                "</body>\n" +
                "</html>";
    }

    public void sendReminder(String emailUser,String firstName,String deadline, int reminderDate) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

        String htmlMessage = reminderMessage(firstName,deadline,reminderDate);

        message.setContent(htmlMessage,"text/html");

        helper.setTo(emailUser);
        helper.setSubject("DEADLINE REMINDER");
        javaMailSender.send(message);
    }

    public String reminderMessage(String firstName,String deadline, int reminderDate){
        return "<html>\n" +
                "<body>\n" +
                "\t<h2>Email remind deadline</h2>\n" +
                "\t<p>Dear "+firstName+" ,</p>\n" +
                "\t<p>Your deadline :"+deadline+" has "+reminderDate+" days left </p><br>\n" +
                "\t<p>Do not miss your deadline</p><br>\n" +
                "\t<p>Thanks for using our product. Have a nice day!</p>\n" +
                "</body>\n" +
                "</html>";
    }
}
