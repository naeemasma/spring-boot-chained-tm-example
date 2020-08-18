package com.example.repository.eventFuture;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.eventFuture.EventFuture;

public interface EventFutureRepository extends JpaRepository<EventFuture, Integer> {

}
