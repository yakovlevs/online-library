package com.example.onlinelibrary.persistence;

import com.example.onlinelibrary.domain.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByUserName(@NonNull String username) {
        return userRepository.findByUsername(username);
    }

    public void save(@NonNull User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    private void add(@NonNull List<String> list, @NonNull String user) {
        list.add(user);
    }

    public Optional<User> findById(@NonNull Long id) {
        return userRepository.findById(id);
    }
}

