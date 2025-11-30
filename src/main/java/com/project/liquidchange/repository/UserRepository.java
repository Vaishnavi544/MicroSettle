package com.project.liquidchange.repository;

import com.project.liquidchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<User, Long> means:
// "I manage 'User' objects, and their primary key is a 'Long' type."
public interface UserRepository extends JpaRepository<User, Long> {

    // Magic Method: We just write the name, Spring writes the SQL!
    // SQL generated: SELECT * FROM users WHERE phone = ?
    Optional<User> findByPhone(String phone);
}