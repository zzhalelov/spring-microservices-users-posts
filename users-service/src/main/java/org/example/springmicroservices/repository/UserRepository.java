package org.example.springmicroservices.repository;

import org.example.springmicroservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}