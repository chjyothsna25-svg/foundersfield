package com.project.foundersfeild.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class ideas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ideaId;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255, message = "Title too long")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Column(length = 2000)
    private String description;

    private String category;
    private int likes = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean secured = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"ideas", "feedbacks", "password"})
    private user user;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("idea")
    private List<Feedback> comments = new ArrayList<>();

    // 🧠 Users who liked this idea
    @ManyToMany
    @JoinTable(
        name = "idea_likes",
        joinColumns = @JoinColumn(name = "idea_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"ideas", "feedbacks", "password"})
    private Set<user> likedUsers = new HashSet<>();

    public ideas() {}

    public ideas(String title, String description, user user, String category) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.category = category;
    }

    // Getters and Setters
    public Long getIdeaId() { return ideaId; }
    public void setIdeaId(Long ideaId) { this.ideaId = ideaId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isSecured() { return secured; }
    public void setSecured(boolean secured) { this.secured = secured; }

    public user getUser() { return user; }
    public void setUser(user user) { this.user = user; }

    public List<Feedback> getComments() { return comments; }
    public void setComments(List<Feedback> comments) { this.comments = comments; }

    public Set<user> getLikedUsers() { return likedUsers; }
    public void setLikedUsers(Set<user> likedUsers) { this.likedUsers = likedUsers; }
}

