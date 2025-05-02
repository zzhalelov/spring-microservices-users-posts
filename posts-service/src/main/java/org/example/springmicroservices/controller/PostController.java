package org.example.springmicroservices.controller;

import org.example.springmicroservices.PostRepository;
import org.example.springmicroservices.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final RestTemplate restTemplate;
    private final PostRepository postRepository;

    public PostController(@Value("${users.server.url}") String url,
                          RestTemplateBuilder builder,
                          PostRepository postRepository) {
        this.postRepository = postRepository;
        this.restTemplate = builder.uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        String url = "/users/" + post.getAuthorId();
        String responseBody = restTemplate.getForObject(url, String.class);
        System.out.println(responseBody);

        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}