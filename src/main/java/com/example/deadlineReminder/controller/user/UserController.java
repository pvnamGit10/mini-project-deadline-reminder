package com.example.deadlineReminder.controller.user;


import com.example.deadlineReminder.entity.todo.Todo;
import com.example.deadlineReminder.entity.user.LogInRequest;
import com.example.deadlineReminder.entity.user.RegistrationRequest;
import com.example.deadlineReminder.entity.user.UserAccount;
import com.example.deadlineReminder.entity.user.UserRole;
import com.example.deadlineReminder.service.todo.TodoService;
import com.example.deadlineReminder.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final TodoService todoService;


    @RequestMapping("/")
    public String homePage(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            return "redirect:/todoList";
        }
        Cookie[] cookies = request.getCookies();
        String current_email = null;
        for(Cookie c : cookies)
        {
            if(c.getName().equals("current_user_cookie")){
                current_email = c.getValue();
            }
        }
        if(current_email!=null) {
            session = request.getSession();
            session.setAttribute("current_user", userService.getUserAccount(current_email));
            return "redirect:/todoList";
        }else
            return "homePage";
    }

    @RequestMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("registrationForm",new RegistrationRequest());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String createAccount(@Valid @ModelAttribute("registrationForm") RegistrationRequest request,
                                BindingResult result) throws MessagingException {
        if(result.hasErrors()){
            return "registration";
        }
        UserAccount account = new
                UserAccount(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
        );

        userService.signUp(account);
        return "redirect:/";
    }

    @PostMapping(value = "/login")
    public String logIn(@ModelAttribute("loginForm")LogInRequest requestForm,
            HttpServletRequest request, HttpServletResponse response){

        boolean check = userService.signIn(requestForm);
        if(check) {
            if(request.getParameter("remember_me")!=null){
                Cookie cookie = new Cookie("current_user_cookie",requestForm.getEmail());
                cookie.setMaxAge(2592000);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            HttpSession session = request.getSession();
            session.setAttribute("current_user",userService.getUserAccount(requestForm.getEmail()));
            return "redirect:/todoList";
        }else
            return "homePage";
    }

    @RequestMapping("/todoList")
    public String todoListPage(Model model){
        List<Todo> listTodo = todoService.listTodo();
        model.addAttribute("listTodo",listTodo);
        model.addAttribute("newTodo",new Todo());
        return "todoList";
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for(Cookie c: cookies){
            if(c.getName().equals("current_user_cookie")){
                Cookie cookie = new Cookie("current_user_cookie", null);
                cookie.setMaxAge(0);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/profile")
    public String profile(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserAccount account = (UserAccount) session.getAttribute("current_user");
        model.addAttribute("account", account);
        return "profile";
    }

    @GetMapping("confirm")
    public String accountConfirmation(@RequestParam(name = "token") String token){
        boolean check = userService.confirmToken(token);
        if ((check)){
            return "redirect:/";
        }else
            return "redirect:/404";
    }
}
