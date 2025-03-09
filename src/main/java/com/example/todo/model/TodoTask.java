package com.example.todo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoTask {
    private Long id;
    private LocalDateTime dueDate;
    @JsonProperty("taskName")
    private String taskName;
    private String formattedDueDate;

 // コンストラクタ
    public TodoTask() {}
    public TodoTask(String taskName) {
        this.taskName = taskName;
    }
    public TodoTask(Long id, String taskName) {
        this.id = id;
        this.taskName = taskName;
    }
    public TodoTask(String taskName, LocalDateTime dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
    }
    
 // ゲッター・セッター
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public String getFormattedDueDate() {
        return formattedDueDate;
    }
    public void setFormattedDueDate(String formattedDueDate) {
        this.formattedDueDate = formattedDueDate;
    }
    
    
}