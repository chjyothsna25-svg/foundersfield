package com.project.foundersfeild.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foundersfeild.model.Feedback;
import com.project.foundersfeild.model.ideas;
import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.feedbackrepo;
import com.project.foundersfeild.repository.idearepo;
import com.project.foundersfeild.repository.userrepo;

@Service
public class feedbackser {
    
    @Autowired
    private feedbackrepo repo;

    @Autowired
    private idearepo idearepo;

    @Autowired
    private userrepo userrepo;

    public List<Feedback> getAllFeedbacks() {
        return repo.findAll();
    }

    public List<Feedback> getFeedbacksForIdea(Long ideaId) {
        ideas idea = idearepo.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        return repo.findByIdeaOrderByCreatedAtDesc(idea);
    }


    public Feedback addFeedback(Long ideaId, Long userId, Feedback feedback) {
        ideas idea = idearepo.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        user user = userrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        feedback.setIdea(idea);
        feedback.setUser(user);
        feedback.setCreatedAt(LocalDateTime.now());

        return repo.save(feedback);
    }

    public Feedback getFeedbackById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteFeedback(Long id) {
        repo.deleteById(id);
    }
    
}