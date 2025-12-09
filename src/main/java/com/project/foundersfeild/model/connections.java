package com.project.foundersfeild.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class connections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private user receiver;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private user requester;

    @Enumerated(EnumType.STRING)
    private Connectionstatus status;

    private LocalDateTime created_at;

    public connections() {
        // Default constructor required by JPA
    }

    public connections(user requester, user receiver, Connectionstatus status) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
        this.created_at = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public user getReceiver() { return receiver; }
    public void setReceiver(user receiver) { this.receiver = receiver; }

    public user getRequester() { return requester; }
    public void setRequester(user requester) { this.requester = requester; }

    public Connectionstatus getStatus() { return status; }
    public void setStatus(Connectionstatus status) { this.status = status; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}
