package com.project.foundersfeild.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.project.foundersfeild.model.Connectionstatus;
import com.project.foundersfeild.model.connections;
import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.conrep;
import com.project.foundersfeild.repository.userrepo;

@Service
public class conser {

    @Autowired
    private conrep repo;

    @Autowired
    private userrepo userrepo;

    // ✅ Send connection request
    public connections sendreq(Long requesterId, Long receiverId) {
        user requester = userrepo.findById(requesterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requester not found"));
        user receiver = userrepo.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver not found"));

        // Prevent duplicate requests
        if (repo.findByRequesterAndReceiver(requester, receiver).isPresent()
                || repo.findByRequesterAndReceiver(receiver, requester).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Connection request already exists");
        }

        connections connection = new connections(requester, receiver, Connectionstatus.PENDING);
        return repo.save(connection);
    }

    // ✅ Accept request
    public connections accreq(Long connectionId) {
        connections connection = repo.findById(connectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        connection.setStatus(Connectionstatus.ACCEPTED);
        return repo.save(connection);
    }

    // ✅ Reject request
    public connections rejreq(Long connectionId) {
        connections connection = repo.findById(connectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        connection.setStatus(Connectionstatus.REJECTED);
        return repo.save(connection);
    }

    // ✅ Get all accepted connections for a user
    public List<connections> getcon(Long userId) {
        user u = userrepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return repo.findByRequesterOrReceiver(u, u);
    }

    // ✅ Check if two users are connected or pending
    public String checkConnectionStatus(Long user1Id, Long user2Id) {
        user user1 = userrepo.findById(user1Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User 1 not found"));
        user user2 = userrepo.findById(user2Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User 2 not found"));

        var c1 = repo.findByRequesterAndReceiver(user1, user2);
        var c2 = repo.findByRequesterAndReceiver(user2, user1);

        if (c1.isPresent()) {
            return c1.get().getStatus().name();
        } else if (c2.isPresent()) {
            return c2.get().getStatus().name();
        }
        return "NONE";
    }
}

