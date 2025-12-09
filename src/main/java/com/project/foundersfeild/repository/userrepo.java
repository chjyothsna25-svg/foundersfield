package com.project.foundersfeild.repository;

import com.project.foundersfeild.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface userrepo extends JpaRepository<user, Long> {
    Optional<user> findByEmail(String email);
    List<user> findByNameContainingIgnoreCase(String name);
}
