package com.example.service;

import com.example.domain.eventFuture.EventFuture;
import com.example.domain.eventMessage.EventMessage;

public interface EventService {
	public void insert(EventMessage eventMessage, EventFuture eventFuture) throws Exception;

}
