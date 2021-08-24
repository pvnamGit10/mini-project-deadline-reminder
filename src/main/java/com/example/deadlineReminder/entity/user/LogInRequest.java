package com.example.deadlineReminder.entity.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LogInRequest {
    private String email;
    private String password;
}
