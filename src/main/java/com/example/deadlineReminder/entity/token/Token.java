package com.example.deadlineReminder.entity.token;

import com.example.deadlineReminder.entity.user.UserAccount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false )
    private String token;
    @Column( nullable = false )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatAt;
    @Column( nullable = false )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime confirmAt;
    private Boolean isConfirmed = false;
    @ManyToOne
    @JoinColumn(name = "user_account_user_id", referencedColumnName = "id")
    private UserAccount userAccount;

    public Token(String token, LocalDateTime creatAt, LocalDateTime expiresAt, UserAccount userAccount) {
        this.token = token;
        this.creatAt = creatAt;
        this.expiresAt = expiresAt;
        this.userAccount = userAccount;
    }
}
