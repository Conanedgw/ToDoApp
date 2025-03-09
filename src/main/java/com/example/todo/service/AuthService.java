package com.example.todo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ユーザー認証 - User オブジェクトを返すように変更
    public User authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        // ユーザーが存在し、パスワードが一致するか確認
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();  // 認証に成功した場合は User を返す
        }
        return null;  // 認証に失敗した場合は null を返す
    }

    // ユーザーID取得
    public Long getUserId(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        // ユーザーが存在し、パスワードが一致するか確認
        return user.filter(u -> u.getPassword().equals(password))
                   .map(User::getId)
                   .orElse(null);  // IDを返す
    }

    // ユーザー登録
    public void registerUser(String username, String password) {
        userRepository.save(new User(null, username, password));
    }
}

