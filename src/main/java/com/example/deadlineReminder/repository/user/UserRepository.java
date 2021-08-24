package com.example.deadlineReminder.repository.user;

import com.example.deadlineReminder.entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);

    @Query("select u from UserAccount u where u.enabled = true and u.email=?1")
    Optional<UserAccount> findByEmailAndEnabled(String email);

    @Query("select u.email from UserAccount u where u.enabled =false and u.email=?1")
    Optional<UserAccount> findByEmailAndDisabled(String email);

    @Transactional
    @Modifying
    @Query("update UserAccount u set u.enabled = true where u.id =?1")
    void enableAccount(Long id);
}
