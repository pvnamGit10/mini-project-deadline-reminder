package com.example.deadlineReminder.controller.todo;

import com.example.deadlineReminder.entity.error.ErrorHandling;
import com.example.deadlineReminder.entity.todo.Todo;
import com.example.deadlineReminder.service.todo.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private  ErrorHandling errorHandling;

   @RequestMapping("/addNewTodo")
    public String addNewTodo(Model model){

         return "redirect:/todoList";
    }

    @PostMapping("/addNewTodo")
    public String addNewTodoToList(@Valid @ModelAttribute("newTodo")Todo todo, BindingResult result){
        System.out.println(result.hasErrors());
        if(result.hasErrors()){
            return "todoList";
        }
        todoService.createTodo(todo);
        return "redirect:/todoList";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTodo(@PathVariable(name = "id")Long id){
        todoService.deleteTodo(id);
        return "redirect:/todoList";
    }

    @RequestMapping("/{id}")
    public String showUpdateTodo(@PathVariable(name = "id")Long id, Model model){
        //ModelAndView modelAndView = new ModelAndView("edit_todo");
        model.addAttribute("todoForm",todoService.getTodo(id));
        return "edit_todo";
    }

    @PostMapping(value = "/editTodo")
    public String updateTodo(@ModelAttribute(name = "todoForm")Todo todo,Model model, HttpServletRequest request){
        todoService.updateTodo(todo);
        return "redirect:/todoList";
    }

    @RequestMapping("/done/{id}")
    public String complete(@PathVariable(name = "id")Long id){
        todoService.makeComplete(id);
        return "redirect:/todoList";
    }
    @RequestMapping("/done-result")
    public String showDonePage(Model model){
        List<Todo> listDone = todoService.getListDone();
        model.addAttribute("listDone",listDone);
        return "done-result";
    }

    @RequestMapping("/in-ready")
    public ModelAndView showInReadyPage(){
        ModelAndView modelAndView = new ModelAndView("in-ready-result");
        List<Todo> listInReady = todoService.getListInReady();
        modelAndView.addObject("listInReady",listInReady);
        return modelAndView;
    }

    @RequestMapping("/in-process")
    public ModelAndView showInProcessPage(){
        ModelAndView modelAndView = new ModelAndView("in-process-result");
        List<Todo> listInProcess = todoService.getListInProcess();
        modelAndView.addObject("listInProcess",listInProcess);
        return modelAndView;
    }


    @RequestMapping(value = "/search-result", method = RequestMethod.GET)
    public ModelAndView searchResult(HttpServletRequest request){
       String searchValue = request.getParameter("searchValue");
       ModelAndView modelAndView = new ModelAndView("search-result");
       List<Todo> listSearch = todoService.getSearchResult(searchValue);
       modelAndView.addObject("searchResult",listSearch);
       return modelAndView;
    }
}
