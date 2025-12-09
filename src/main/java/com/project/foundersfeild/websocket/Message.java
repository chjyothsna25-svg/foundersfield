package com.project.foundersfeild.websocket;

import com.project.foundersfeild.model.user;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private user sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private user receiver;

    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

    public Message() {}

    public Message(user sender, user receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public user getSender() { return sender; }
    public void setSender(user sender) { this.sender = sender; }

    public user getReceiver() { return receiver; }
    public void setReceiver(user receiver) { this.receiver = receiver; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

