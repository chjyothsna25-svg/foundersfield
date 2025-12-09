package com.project.foundersfeild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.foundersfeild.model.Feedback;
import com.project.foundersfeild.service.feedbackser;

import jakarta.validation.Valid;

@RestController
public class feedbackcon {

    @Autowired
    private feedbackser ser;

    @PostMapping("/ideas/{ideaId}/feedback")
    public Feedback createFeedback(@PathVariable Long ideaId,
                                   @RequestParam Long userId,
                                   @Valid @RequestBody Feedback feedback) {
        return ser.addFeedback(ideaId, userId, feedback);
    }

    @GetMapping("/feedback")
    public List<Feedback> getAllFeedbacks() {
        return ser.getAllFeedbacks();
    }

    @GetMapping("/ideas/{ideaId}/feedback")
    public List<Feedback> getFeedbacksForIdea(@PathVariable Long ideaId) {
        return ser.getFeedbacksForIdea(ideaId);
    }

    @GetMapping("/feedback/{id}")
    public Feedback getFeedback(@PathVariable Long id) {
        return ser.getFeedbackById(id);
    }

    @DeleteMapping("/feedback/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        ser.deleteFeedback(id);
    }
}