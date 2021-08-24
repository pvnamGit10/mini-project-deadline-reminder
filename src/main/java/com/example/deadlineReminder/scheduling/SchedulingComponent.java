package com.example.deadlineReminder.scheduling;

import com.example.deadlineReminder.entity.todo.Todo;
import com.example.deadlineReminder.repository.todo.TodoRepository;
import com.example.deadlineReminder.service.todo.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
@AllArgsConstructor
public class SchedulingComponent {
    private final TodoService todoService;
    private final TodoRepository todoRepository;
    public void checkStatus(){
        List<Todo> deadlineList = todoRepository.findAll();
        for(Todo todo:deadlineList){
            todoService.todoCheckStatus(todo);
        }
    }
    public void sendEmailReminder() throws MessagingException {
        List<Todo> deadlineList = todoRepository.findAll();
        for(Todo todo :deadlineList){
            if(todo.getInProcess()){
                todoService.sendEmail(todo);
            }
        }
    }
}
