package com.project.foundersfeild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.foundersfeild.model.ideas;

@Repository
public interface idearepo extends JpaRepository<ideas, Long> {

    // Existing derived method (keeps it for fallback)
    List<ideas> findByCategoryOrderByLikesDesc(String category);

    // Fetch the user with each idea (for category endpoint)
    @EntityGraph(attributePaths = {"user"})
    List<ideas> findByCategoryOrderByLikesDescWithUser(String category);

    // Fetch user for findAll
    @EntityGraph(attributePaths = {"user"})
    List<ideas> findAllWithUser();

    // Fetch user by id (single result)
    @EntityGraph(attributePaths = {"user"})
    Optional<ideas> findByIdeaId(Long ideaId);

    // Fetch by user id (user's own ideas)
    @EntityGraph(attributePaths = {"user"})
    List<ideas> findByUserIdWithUser(Long userId);

    // Keep this for compatibility if needed
    List<ideas> findByUserId(Long userId);
}
