package com.example.deadlineReminder.service.todo;

import com.example.deadlineReminder.repository.todo.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TodoCheck {
    private final TodoRepository todoRepository;

    public Boolean isDone(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        if(LocalDate.now().isAfter(endDate)){
            return true;
        }else {
            return false;
        }
    }

    public Boolean inProcess(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        if((LocalDate.now().isAfter(startDate)&&LocalDate.now().isBefore(endDate))
            || LocalDate.now().isEqual(startDate) || LocalDate.now().isEqual(endDate)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean inReady(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate){
        if(LocalDate.now().isBefore(startDate)){
            return true;
        }else {
            return false;
        }
    }

}
