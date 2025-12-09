package com.project.foundersfeild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foundersfeild.model.Connectionstatus;
import com.project.foundersfeild.model.connections;
import com.project.foundersfeild.model.user;

public interface conrep extends JpaRepository<connections, Long> {

    List<connections> findByRequesterOrReceiver(user requester, user receiver);

    List<connections> findByReceiverAndStatus(user receiver, Connectionstatus status);

    Optional<connections> findByRequesterAndReceiver(user requester, user receiver);

    Optional<connections> findByRequesterAndReceiverAndStatus(user requester, user receiver, Connectionstatus status);
}
