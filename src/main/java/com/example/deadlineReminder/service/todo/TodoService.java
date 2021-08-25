package com.example.deadlineReminder.service.todo;

import com.example.deadlineReminder.entity.error.ErrorHandling;
import com.example.deadlineReminder.entity.todo.Todo;
import com.example.deadlineReminder.entity.user.UserAccount;
import com.example.deadlineReminder.mailSender.EmailSender;
import com.example.deadlineReminder.mailSender.EmailServiceImpl;
import com.example.deadlineReminder.repository.todo.TodoRepository;
import com.example.deadlineReminder.repository.user.UserRepository;
import com.example.deadlineReminder.service.errorService.todoError.TodoError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoCheck todoCheck;
    private final EmailServiceImpl emailService;
    public List<Todo> listTodo(){
        HttpSession session = request.getSession();
        UserAccount current_user = (UserAccount)session.getAttribute("current_user");
        return todoRepository.getListTodo(current_user);
    }

    //check status of deadline day by day
    @Transactional
    public void todoCheckStatus(Todo todo){
        if(todoRepository.isDone(todo.getId()).isPresent()){
            return;
        }
            Boolean isDone = todoCheck.isDone(todo.getEndOn());
            Boolean inProcess = todoCheck.inProcess(todo.getBeginOn(),todo.getEndOn());
            Boolean inReady = todoCheck.inReady(todo.getBeginOn());
            todoRepository.checkStatus(isDone,inProcess,inReady,todo.getId());
    }

    private final HttpServletRequest request;


    //Add deadline to database
    @Transactional
    public void createTodo(Todo todo){
        HttpSession session = request.getSession();
        todo.setUserAccount((UserAccount) session.getAttribute("current_user"));
        todoRepository.save(todo);
        todoCheckStatus(todo);
    }

    //Get deadline description through ID
    public Todo getTodo(Long id){
        return todoRepository.getById(id);
    }

    //Delete deadline through ID
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.getById(id);
        todoRepository.delete(todo);
    }

    //Update deadline through ID
    @Transactional
    public boolean updateTodo(Todo todo){
        todoRepository.updateTodo(todo.getTitle(),todo.getDes(),todo.getBeginOn(),todo.getEndOn(),todo.getId());
        todoCheckStatus(todo);
        return true;
    }

    //When client check complete, this function will be called to make isDone=true in DB
    @Transactional
    public void makeComplete(Long id){
        todoRepository.makeComplete(id);
    }

    public List<Todo> getListDone(){
        HttpSession session = request.getSession();
        UserAccount current_user = (UserAccount)session.getAttribute("current_user");
        return todoRepository.getLisDone(current_user);
    }

    public List<Todo> getListInReady(){
        HttpSession session = request.getSession();
        UserAccount current_user = (UserAccount)session.getAttribute("current_user");
        return todoRepository.getListInReady(current_user);
    }

    public List<Todo> getListInProcess(){
        HttpSession session = request.getSession();
        UserAccount current_user = (UserAccount) session.getAttribute("current_user");
        return todoRepository.getListInProcess(current_user);
    }

    public List<Todo> getSearchResult(String searchValue){
        HttpSession session = request.getSession();
        UserAccount current_user = (UserAccount) session.getAttribute("current_user");
        return todoRepository.getListSearch(searchValue,current_user);
    }

    
    //send email to user, remind their time left for deadline
    public void sendEmail(Todo todo) throws MessagingException {
        int reminderDate = todo.getEndOn().compareTo(LocalDate.now());
        String emailUser = todo.getUserAccount().getEmail();
        String firstName = todo.getUserAccount().getFirstName();
        String deadline = todo.getTitle();
        emailService.sendReminder(emailUser,firstName,deadline,reminderDate);
    }
}
