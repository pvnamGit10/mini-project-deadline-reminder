package com.example.deadlineReminder.entity.error;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString
public class ErrorHandling {

    private String errorTodoDate;
    private String errorPasswordLength;
    private String errorEmailFormat;
    private String errorConfirmPassword;
    private String errorTokenIsExpired;
    private String errorTitleIsEmpty;
    private String dateEmpty;
    private String errorDesTodoLength;
    private String errorDateSet;
}
