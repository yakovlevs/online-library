package com.example.onlinelibrary.services;

import com.example.onlinelibrary.domain.Books;
import com.example.onlinelibrary.domain.Role;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.persistence.UserDao;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @PostConstruct
    public void init() {
        if (!userDao.findByUserName("user").isPresent()) {
            userDao.save(User.builder()
                    .username("user")
                    .password(new BCryptPasswordEncoder().encode("password"))
                    .authorities(ImmutableList.of(Role.USER))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .favoriteBooks(new HashSet<>())
                    .build());
            userDao.save(User.builder()
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .authorities(ImmutableList.of(Role.ADMIN))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .favoriteBooks(new HashSet<>(
                            Arrays.asList(
                                    Books.builder().googleId("fbM1DwAAQBAJ").build(),
                                    Books.builder().googleId("twKQ7zpid2UC").build(),
                                    Books.builder().googleId("eavbBQAAQBAJ").build(),
                                    Books.builder().googleId("5NomkK4EV68C").build())))
                    .build());
            userDao.save(User.builder()
                    .username("power")
                    .password(new BCryptPasswordEncoder().encode("power"))
                    .authorities(ImmutableList.of(Role.POWER_USER))
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .favoriteBooks(new HashSet<>())
                    .build());
        }
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userDao.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException("user " + username + " was not found."));
    }

    public Optional<User> findByUserName(@NonNull String username) {
        return userDao.findByUserName(username);
    }

    public Optional<User> findById(@NonNull Long id) {
        return userDao.findById(id);
    }
}
