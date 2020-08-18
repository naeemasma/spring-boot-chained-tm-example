package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.eventFuture.EventFuture;
import com.example.domain.eventMessage.EventMessage;
import com.example.repository.eventFuture.EventFutureRepository;
import com.example.repository.eventMessage.EventMessageRepository;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventMessageRepository eventMessageRepository;
	
	@Autowired
	private EventFutureRepository eventFutureRepository;
		
	@Transactional(value="chainedTransactinManager", rollbackFor = Exception.class)
	@Override
	public void insert(EventMessage eventMessage, EventFuture eventFuture) throws Exception {
		eventMessageRepository.save(eventMessage);
		eventFuture.setId(eventMessage.getId());
		eventFutureRepository.save(eventFuture);
	}
}
