package com.example.repository.eventMessage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.eventMessage.EventMessage;

public interface EventMessageRepository extends JpaRepository<EventMessage, Integer> {

}
