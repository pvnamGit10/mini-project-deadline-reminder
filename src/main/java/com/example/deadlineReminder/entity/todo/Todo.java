package com.example.deadlineReminder.entity.todo;

import com.example.deadlineReminder.entity.user.UserAccount;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must be filled in")
    private String title;

    @Size(max = 300, message = "Description limit length in 300 char")
    private String des;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginOn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOn;
    private Boolean isDone = false;
    private Boolean inProcess = false;
    private Boolean inReady = false;
    @ManyToOne
    @JoinColumn(name = "user_account_user_id", referencedColumnName = "id")
    private UserAccount userAccount;


    public Todo(String title, String des, LocalDate beginOn, LocalDate endOn,UserAccount userAccount) {
        this.title = title;
        this.des = des;
        this.beginOn = beginOn;
        this.endOn = endOn;
        this.userAccount = userAccount;
    }
}
