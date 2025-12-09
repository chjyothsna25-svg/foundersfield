package com.project.foundersfeild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.foundersfeild.model.ideas;
import com.project.foundersfeild.service.ideaser;
import com.project.foundersfeild.service.pdfgenerator;

import jakarta.validation.Valid;

@RestController
public class ideacon {

    @Autowired
    private ideaser ser;

    @Autowired
    private pdfgenerator pdfGen;

    // ✅ Post a new idea (with PDF generation if secured)
    @PostMapping("/ideas")
    public void postideas(@Valid @RequestBody ideas idea,
                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {

        // Save the idea first
        ideas savedIdea = ser.postidea(idea, userDetails.getUsername());

        // Generate PDF if idea is marked as secured
        if (savedIdea.isSecured()) {
            try {
                String pdfPath = pdfGen.generateIdeaPdf(savedIdea);
                System.out.println("✅ PDF successfully generated for secured idea at: " + pdfPath);
            } catch (Exception e) {
                System.err.println("❌ Failed to generate PDF for idea ID: " + savedIdea.getIdeaId());
                e.printStackTrace();
            }
        }
    }

    // ✅ Get all ideas
    @GetMapping("/ideas")
    public List<ideas> getideas() {
        return ser.getideas();
    }

    // ✅ Get idea by ID
    @GetMapping("/ideas/{id}")
    public ideas getidea(@PathVariable Long id) {
        return ser.getidea(id);
    }

    // ✅ Update idea
    @PutMapping("/ideas/{id}")
    public ideas updateideas(@PathVariable Long id, @RequestBody ideas idea) {
        return ser.updateIdea(id, idea);
    }

    // ✅ Delete idea
    @DeleteMapping("/ideas/{id}")
    public String deleteidea(@PathVariable Long id) {
        ser.deleteIdea(id);
        return "Idea deleted";
    }

    // ✅ Secure an existing idea (also generates PDF)
    @PostMapping("/ideas/{ideaId}/secure")
    public ideas secureIdea(@PathVariable Long ideaId, @RequestParam Long userId) {
        ideas securedIdea = ser.secureIdea(ideaId, userId);
        if (securedIdea.isSecured()) {
            try {
                String pdfPath = pdfGen.generateIdeaPdf(securedIdea);
                System.out.println("✅ PDF successfully generated for secured idea at: " + pdfPath);
            } catch (Exception e) {
                System.err.println("❌ Failed to generate PDF for idea ID: " + securedIdea.getIdeaId());
                e.printStackTrace();
            }
        }
        return securedIdea;
    }

    // ✅ Get ideas by category
    @GetMapping("/ideas/category/{category}")
    public List<ideas> getIdeasByCategory(@PathVariable String category) {
        return ser.getIdeasByCategory(category);
    }

    @PostMapping("/ideas/{id}/like")
    public ResponseEntity<?> likeIdea(@PathVariable Long id,
                                      @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        ideas idea = ser.getidea(id);
        com.project.foundersfeild.model.user loggedUser =
            ser.findUserByEmail(userDetails.getUsername());

        // Check if user already liked
        if (idea.getLikedUsers().contains(loggedUser)) {
            return ResponseEntity.badRequest().body("You already liked this idea");
        }

        // Add like
        idea.getLikedUsers().add(loggedUser);
        idea.setLikes(idea.getLikedUsers().size());
        ser.saveIdea(idea);

        return ResponseEntity.ok(idea);
    }


    // ✅ Get all ideas posted by a specific user
    @GetMapping("/api/ideas/user/{userId}")
    public List<ideas> getIdeasByUser(@PathVariable Long userId) {
        return ser.getIdeasByUser(userId);
    }
}
