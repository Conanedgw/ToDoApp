package com.example.todo.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.model.TodoTask;
import com.example.todo.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final SessionService sessionService; // 追加

    public TodoService(TodoRepository todoRepository, SessionService sessionService) { // 修正
        this.todoRepository = todoRepository;
        this.sessionService = sessionService;
    }
    
    // タスクの追加（ユーザーIDをセット）
    public void addTask(TodoTask todoTask) {
        Long userId = sessionService.getUserId();
        if (userId == null) {
            throw new IllegalStateException("ログインしていません");
        }
        todoTask.setId(userId); // ユーザーIDをセット
        todoRepository.addTask(todoTask);
    }

    // ログイン中のユーザーのタスクを取得
    public List<TodoTask> getTasks() {
        Long userId = sessionService.getUserId();
        if (userId == null) {
            return List.of(); // ログインしていない場合は空リスト
        }

        List<TodoTask> tasks = todoRepository.getTasksByUserId(userId); // 修正
        for (TodoTask task : tasks) {
            if (task.getDueDate() != null) {
                task.setFormattedDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            } else {
                task.setFormattedDueDate("未設定");
            }
        }

        return tasks;
    }

    // タスク削除（ユーザーIDを考慮）
    public void deleteTask(Long id) {
        Long userId = sessionService.getUserId();
        if (userId == null) {
            throw new IllegalStateException("ログインしていません");
        }
        todoRepository.deleteTask(id, userId); // ユーザーIDを考慮するように修正
    }

    // タスク更新（ユーザーIDを考慮）
    public boolean updateTask(Long id, String taskName) {
        Long userId = sessionService.getUserId();
        if (userId == null) {
            throw new IllegalStateException("ログインしていません");
        }
        int rowsAffected = todoRepository.updateTask(id, taskName, userId); // 修正
        return rowsAffected > 0;
    }
}