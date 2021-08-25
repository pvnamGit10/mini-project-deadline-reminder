package com.example.deadlineReminder.service.user;


import com.example.deadlineReminder.entity.token.Token;
import com.example.deadlineReminder.entity.user.LogInRequest;
import com.example.deadlineReminder.entity.user.UserAccount;

import com.example.deadlineReminder.mailSender.EmailServiceImpl;
import com.example.deadlineReminder.repository.token.TokenRepository;
import com.example.deadlineReminder.repository.user.UserRepository;
import com.example.deadlineReminder.security.PasswordEncoder;
import com.example.deadlineReminder.service.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    public void signUp(UserAccount account) throws MessagingException {
        boolean userExists = userRepository.findByEmailAndEnabled(account.getEmail()).isPresent();
        if(userExists) // if email is already enabled, throw 500 error page 
        {
            throw new IllegalStateException("Email already existed");
        }
        if(userRepository.findByEmailAndDisabled(account.getEmail()).isPresent()){ // if email is disabled, delete this user in database, reassign a user 
            UserAccount userAccount = userRepository.findByEmail(account.getEmail()).get();
            tokenRepository.deleteByUserAccount(userAccount);
            userRepository.delete(userAccount);
        }
        account.setPassword(passwordEncoder.encoder().encode(account.getPassword()));
        userRepository.save(account); // save user to DB

        //TODO: send token
        String stringOfToken = UUID.randomUUID().toString();
        Token token = new Token(
            stringOfToken,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            account
        );
        tokenService.saveToken(token);
        System.out.println(stringOfToken); 
        //send email with token was created in databse match with user account.
        emailService.sendEmail(account.getEmail(),stringOfToken);
    }


    public UserAccount getUserAccount(String email){
        return userRepository.findByEmailAndEnabled(email).get();
    }

    public boolean signIn(LogInRequest request) {
        boolean userExists = userRepository.findByEmailAndEnabled(request.getEmail()).isPresent();
        if(!userExists)
        {
            System.out.println("Email not existed");
            return false;

        }
        String passwordFromDB = userRepository.findByEmailAndEnabled(request.getEmail()).get().getPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(request.getPassword(), passwordFromDB)){
            return true;
        }else
        {
            return false;
        }

    }


    public boolean confirmToken(String stringOfToken) {
        tokenService.updateConfirmAt(stringOfToken);
        Token token = tokenRepository.findByToken(stringOfToken).orElseThrow(
                ()->new IllegalStateException("Token not found"));
        LocalDateTime expires = token.getExpiresAt();
        LocalDateTime confirm = token.getConfirmAt();
        if(expires.isBefore(confirm))
            return false;
        if(token.getIsConfirmed()==true)
            return false;
        UserAccount userAccount = tokenService.getTokenConfirmation(stringOfToken);
        userRepository.enableAccount(userAccount.getId());
        tokenRepository.updateIsConfirmed(stringOfToken);
        return true;

    }
}
