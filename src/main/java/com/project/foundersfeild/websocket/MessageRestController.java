package com.project.foundersfeild.websocket;

import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.userrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private userrepo userRepository;

    // Get conversation between logged-in user and another user
    @GetMapping("/history/{userId}")
    public List<Message> getMessageHistory(@PathVariable Long userId, Principal principal) {
        user currentUser = userRepository.findByEmail(principal.getName())
                                         .orElseThrow(() -> new RuntimeException("User not found"));
        user otherUser = userRepository.findById(userId)
                                       .orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepository.findConversation(currentUser.getId(), otherUser.getId());
    }
}

