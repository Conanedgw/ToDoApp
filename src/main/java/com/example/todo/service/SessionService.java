package com.example.todo.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }

    public void login(Long userId, String username) {
        session.setAttribute("userId", userId); // userId も保存
        session.setAttribute("username", username);
    }

    public void logout() {
        session.invalidate();
    }

    public String getLoggedInUser() {
        return (String) session.getAttribute("username");
    }
    
    public Long getUserId() {
        return (Long) session.getAttribute("userId");
    }

    public boolean isLoggedIn() {
        return session.getAttribute("userId") != null;
    }

}

