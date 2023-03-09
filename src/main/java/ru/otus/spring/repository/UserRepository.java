package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    int countByName(String username);
    Optional<User> findByName(String username);
}
