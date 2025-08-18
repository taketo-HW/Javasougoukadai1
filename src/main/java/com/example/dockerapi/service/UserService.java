package com.example.dockerapi.service;

import com.example.dockerapi.model.User;
import com.example.dockerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // すべてのユーザーを取得
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ユーザーIDで検索
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ユーザーを作成
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // ユーザー情報を更新
    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setAddress(newUser.getAddress());
            user.setIsMale(newUser.getIsMale());
            user.setOld(newUser.getOld());
            return userRepository.save(user);
        }).orElse(null);
    }

    // ユーザーを削除
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
