package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todo.model.User;
import com.example.todo.service.AuthService;
import com.example.todo.service.SessionService;

@Controller
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    public AuthController(AuthService authService, SessionService sessionService) {
        this.authService = authService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // ログイン画面を表示
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        // ユーザー認証を行う
        User user = authService.authenticate(username, password);

        if (user != null) {
            sessionService.login(user.getId(), user.getUsername()); // ログイン成功時に userId と username をセッションに保存
            return "redirect:/tasks/view-list";  // タスク一覧へリダイレクト
        } else {
            // 認証失敗時にエラーメッセージをリダイレクト先に渡す
            redirectAttributes.addFlashAttribute("error", "ユーザー名またはパスワードが間違っています");
            return "redirect:/login";  // ログインページにリダイレクト
        }
    }

    @PostMapping("/logout") // ログアウト処理
    public String logout() {
        sessionService.logout();  // セッションを破棄
        return "redirect:/login";  // ログイン画面にリダイレクト
    }
}


