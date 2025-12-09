package com.project.foundersfeild.websocket;

import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private userrepo userRepository;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Map<String, Object> payload, Principal principal) {

        // Sender is always the logged-in user
        user senderUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Read receiverId and content from frontend payload
        Object rid = payload.get("receiverId");
        Object contentObj = payload.get("content");

        if (rid == null || contentObj == null) {
            throw new RuntimeException("Receiver or content missing in payload");
        }

        Long receiverId = Long.valueOf(rid.toString());
        String content = contentObj.toString();

        user receiverUser = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Create and save message
        Message message = new Message();
        message.setSender(senderUser);
        message.setReceiver(receiverUser);
        message.setContent(content);

        messageRepository.save(message);

        return message;
    }
}
