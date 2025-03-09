package com.example.todo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.model.TodoTask;
import com.example.todo.service.SessionService;
import com.example.todo.service.TodoService;


@Controller
@RequestMapping("/tasks")
public class TodoViewController {
    private final TodoService todoService;
    private final SessionService sessionService;

    public TodoViewController(TodoService todoService, SessionService sessionService, HttpSession session) {
        this.todoService = todoService;
        this.sessionService = sessionService;
    }
    
    // ログインしていなければログインページへ
    @GetMapping("/view-list")
    public String listTasks(Model model) {
        if (!sessionService.isLoggedIn()) {
            return "redirect:/login";
        }

        List<TodoTask> tasks = todoService.getTasks();
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        model.addAttribute("tasks", tasks);
        return "list";
    }

    // タスクの追加
    @PostMapping("/add")
    public String addTask(@RequestParam String taskName, @RequestParam String dueDate) {
        // String型で受け取った日付をLocalDateTime型に変換
        LocalDateTime parsedDueDate = LocalDateTime.parse(dueDate);
        TodoTask todoTask = new TodoTask(taskName, parsedDueDate);  // TodoTaskに引数を合わせる
        todoService.addTask(todoTask);  // タスクを追加
        return "redirect:/tasks/view-list";  // タスク一覧にリダイレクト
    }

    // タスクの削除
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        todoService.deleteTask(id);  // タスク削除
        return "redirect:/tasks/view-list";  // 削除後に一覧ページへリダイレクト
    }
}
