package org.example.springmicroservices.controller;

import org.example.springmicroservices.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        user.setId(++nextId);
        user.setCreatedAt(LocalDateTime.now());
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }
}