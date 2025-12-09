package com.project.foundersfeild.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foundersfeild.model.ideas;
import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.idearepo;
import com.project.foundersfeild.repository.userrepo;

@Service
public class ideaser {
	
    @Autowired
    private idearepo repo;
    
    @Autowired
    private pdfgenerator pdf;
    
    @Autowired
    private userrepo userr;
    
    // ✅ Fetch all ideas with user details
    public List<ideas> getideas() {
        return repo.findAllWithUser();
    }
    
    // ✅ Fetch one idea by ID with user details
    public ideas getidea(Long ideaId) {
        return repo.findByIdeaId(ideaId)
                   .orElseThrow(() -> new RuntimeException("Idea not found"));
    }
    
    // ✅ Create new idea and attach user
    public ideas postidea(ideas idea, String email) {
        user owner = userr.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        idea.setUser(owner);
        idea.setCreatedAt(LocalDateTime.now());
        return repo.save(idea);
    }
    
    // ✅ Update idea content
    public ideas updateIdea(Long id, ideas updatedIdea) {
        ideas existingIdea = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        existingIdea.setTitle(updatedIdea.getTitle());
        existingIdea.setDescription(updatedIdea.getDescription());
        existingIdea.setCreatedAt(LocalDateTime.now());
        return repo.save(existingIdea);
    }

    // ✅ Delete idea
    public void deleteIdea(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Idea not found");
        }
        repo.deleteById(id);
    }

    // ✅ Secure idea (with PDF generation)
    public ideas secureIdea(Long ideaId, Long userId) {
        ideas idea = repo.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        if (!idea.getUser().getId().equals(userId)) {
            throw new RuntimeException("Only the owner can secure this idea");
        }

        if (idea.isSecured()) {
            throw new RuntimeException("Idea is already secured");
        }

        idea.setSecured(true);
        repo.save(idea);

        String pdfPath = pdf.generateIdeaPdf(idea);
        System.out.println("✅ PDF generated at: " + pdfPath);

        return idea;
    }
    
    // ✅ Get ideas by category
    public List<ideas> getIdeasByCategory(String category) {
        return repo.findByCategoryOrderByLikesDescWithUser(category);
    }

    // ✅ Like idea (only once per user)
    public ideas likeIdea(Long ideaId, String email) {
        user likedUser = userr.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ideas idea = repo.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        Set<user> likedUsers = idea.getLikedUsers();

        // ⚠️ Prevent duplicate likes
        if (likedUsers.contains(likedUser)) {
            throw new RuntimeException("You already liked this idea!");
        }

        likedUsers.add(likedUser);
        idea.setLikes(likedUsers.size()); // sync like count
        idea.setLikedUsers(likedUsers);

        return repo.save(idea);
    }

    // ✅ Get ideas by user
    public List<ideas> getIdeasByUser(Long userId) {
        return repo.findByUserIdWithUser(userId);
    }

    // ✅ Helper for other services
    public user findUserByEmail(String email) {
        return userr.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Helper to save idea
    public ideas saveIdea(ideas idea) {
        return repo.save(idea);
    }
}

