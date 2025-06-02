package com.intorwacja.securitylabtask.repository;


import com.intorwacja.securitylabtask.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByUserUsername(String username);
}
