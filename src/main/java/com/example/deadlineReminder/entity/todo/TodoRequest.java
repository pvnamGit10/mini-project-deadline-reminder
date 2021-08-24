package com.example.deadlineReminder.entity.todo;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TodoRequest {
    @NotBlank(message = "Title must be filled in")
    private String title;

    private String des;

    @PastOrPresent(message = "Date begin must not be blank")
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @FutureOrPresent(message = "Date end must not be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
