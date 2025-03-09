package com.example.todo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.todo.model.TodoTask;

@Repository
public class TodoRepository  {
    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
 // ユーザーごとのタスク取得
    public List<TodoTask> getTasksByUserId(Long userId) {
        String query = "SELECT id, task_name, due_date FROM tasks WHERE user_id = ?";
        return jdbcTemplate.query(query, taskRowMapper, userId);
    }

    // タスク追加（ユーザーIDを考慮）
    public void addTask(TodoTask todoTask) {
        String query = "INSERT INTO tasks (task_name, due_date, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, todoTask.getTaskName(), todoTask.getDueDate(), todoTask.getId());
    }

    // タスク削除（ユーザーIDを考慮）
    public void deleteTask(Long id, Long userId) {
        String query = "DELETE FROM tasks WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, id, userId);
    }

    // タスク更新（ユーザーIDを考慮）
    public int updateTask(Long id, String taskName, Long userId) {
        String query = "UPDATE tasks SET task_name = ? WHERE id = ? AND user_id = ?";
        return jdbcTemplate.update(query, taskName, id, userId);
    }


    private final RowMapper<TodoTask> taskRowMapper = (rs, rowNum) -> {
        TodoTask task = new TodoTask();
        task.setId(rs.getLong("id"));
        task.setTaskName(rs.getString("task_name"));
        task.setDueDate(rs.getObject("due_date", LocalDateTime.class));  // dueDateをLocalDateTimeとして設定
        return task;
    };
}