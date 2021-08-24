package com.example.deadlineReminder.service.errorService.todoError;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TodoError {
    public String errorStartDateAndEndDate(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate){

        if(startDate.isAfter(endDate)){
            return "Start Date must before or equal End Date";
        }else{
            return null;
        }

    }
}
