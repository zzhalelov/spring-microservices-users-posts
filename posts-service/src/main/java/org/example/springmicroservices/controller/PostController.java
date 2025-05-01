package org.example.springmicroservices.controller;

import org.example.springmicroservices.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final Map<Long, Post> posts = new HashMap<>();
    private final String userServerUrl = "http://localhost:8081";
    private final RestTemplate restTemplate = new RestTemplate();
    private long nextId;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        String url = userServerUrl + "/users/" + post.getAuthorId();
        String responseBody = restTemplate.getForObject(url, String.class);
        System.out.println(responseBody);


        post.setId(++nextId);
        post.setCreatedAt(LocalDateTime.now());
        posts.put(post.getId(), post);
        return post;
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable long id) {
        Post post = posts.get(id);
        if (post == null) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
        return post;
    }
}
