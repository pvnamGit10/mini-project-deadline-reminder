package com.example.deadlineReminder.service.token;

import com.example.deadlineReminder.entity.token.Token;
import com.example.deadlineReminder.entity.user.UserAccount;
import com.example.deadlineReminder.repository.token.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TokenService {


    private final TokenRepository tokenRepository;

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    @Transactional
    public void updateConfirmAt(String token){
        tokenRepository.updateConfirmAt(LocalDateTime.now(),token);
    }

    public UserAccount getTokenConfirmation(String token){
        return tokenRepository.findByToken(token).get().getUserAccount();
    }



}
