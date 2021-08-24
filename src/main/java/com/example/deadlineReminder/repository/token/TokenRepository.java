package com.example.deadlineReminder.repository.token;

import com.example.deadlineReminder.entity.token.Token;
import com.example.deadlineReminder.entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Transactional
    @Modifying
    @Query("delete from Token t where t.userAccount = ?1")
    void deleteByUserAccount(UserAccount userAccount);

    Optional<Token> findByToken(String token);
    @Transactional
    @Modifying
    @Query("update Token t set t.confirmAt =?1 where t.token = ?2")
    void updateConfirmAt(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime confirmAt, String token);

    @Transactional
    @Modifying
    @Query("update Token t set t.isConfirmed =true where t.token = ?1")
    void updateIsConfirmed(String token);

}
