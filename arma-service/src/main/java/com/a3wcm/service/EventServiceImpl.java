package com.a3wcm.service;

import com.a3wcm.domain.Event;
import com.a3wcm.exception.EventException;
import com.a3wcm.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *  Service layer implementation to work with event entities.
 **/
@Service
public class EventServiceImpl implements  EventService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventRepository repository;


    @Override
    public Optional<Event> findEventById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Event> findEventByName(String eventName) {
        if(eventName.isEmpty() || eventName.isBlank()){
            throw new IllegalArgumentException("Provided event name can't be empty or blank");
        }
        return repository.findByNameContains(eventName);
    }

    @Override
    public List<Event> findEventByDescription(String description) {
        if(description.isEmpty() || description.isBlank()){
            throw new IllegalArgumentException("Provided description can't be empty or blank");
        }
        return repository.findByDescriptionContains(description);
    }

    @Override
    public Event findEventByCreatedDate(Date createdDate) {
        return repository.findByCreatedDate(createdDate);
    }

    @Override
    public List<Event> findByModifiedDate(Date modifiedDate) {
        return repository.findByModifiedDate(modifiedDate);
    }

    @Override
    public Event create(Event event) {
        event.setCreatedDate(new Date());
        Event savedEvent = repository.saveAndFlush(event);
        log.info("new training has been created: " + savedEvent.getId());
        return savedEvent;
    }

    @Override
    public Event saveChanges(Event update) {
        if(repository.existsById(update.getId())){
            log.debug("account {} changes have been saved", update.getId());
            return repository.saveAndFlush(update);
        } else {
            throw new EventException("Can't find event with id " + update.getId());
        }
    }
    
    
    
}
