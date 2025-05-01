package org.example.springmicroservices.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    Long id;
    String description;
    Long authorId; //user's id
    LocalDateTime createdAt;
}
