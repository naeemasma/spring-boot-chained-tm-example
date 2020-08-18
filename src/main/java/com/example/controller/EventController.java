package com.example.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.eventFuture.EventFuture;
import com.example.domain.eventMessage.EventMessage;
import com.example.service.EventService;

@Controller
public class EventController  {
	
	@Autowired EventService eventService;    
    
    @ResponseBody
    @RequestMapping(value = "/event/{msg}", method = RequestMethod.GET)
    public Map<String, Object> event(@PathVariable("msg") String msg) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		EventMessage m = new EventMessage();
    		m.setDescription(msg);
    		EventFuture f = new EventFuture();
    		f.setAction(m.getDescription().toUpperCase().startsWith("ERROR")?"TO-BE-DETERMINED":"SCHEDULED");
    		eventService.insert(m, f);

    		Assert.notNull(m.getId());
    		Assert.notNull(f.getId());
    		result.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("exception", e.getMessage());
		}
    	return result;
    }

}
