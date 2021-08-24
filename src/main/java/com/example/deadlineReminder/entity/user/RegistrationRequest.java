package com.example.deadlineReminder.entity.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegistrationRequest {
    @NotBlank(message = "First name must be filled up")
    private String firstName;

    @NotBlank(message = "Last name must be filled up")
    private String lastName;

    @NotBlank(message = "Email must be filled up")
    @Email(message = "Email must be valid form")
    private String email;

    @NotBlank(message = "Pass word not blank")
    @Size(min = 3, max = 20,message = "Password must from 3-20 char")
    private String password;


}
