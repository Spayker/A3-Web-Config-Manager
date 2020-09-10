package com.a3wcm.service;

import com.a3wcm.domain.Event;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *  Service layer interface to provided API for work with event entity.
 **/
public interface EventService {

    /**
     *  Looks for events by provided event name String
     *  @param id - Long value to be used for search
     *  @return found instance of Event
     **/
    Optional<Event> findEventById(Long id);

    /**
     *  Looks for events by provided event name String
     *  @param eventName - String value to be used for search
     *  @return list of found Event entities
     **/
    List<Event> findEventByName(String eventName);

    /**
     *  Looks for events by provided note String
     *  @param description - String value to be used for search
     *  @return list of found Event entities
     **/
    List<Event> findEventByDescription(String description);

    /**
     *  Looks for event by provided created date instance
     *  @param createdDate - instance of util.Date that represents date and time when event was created
     *  @return instance of found event
     **/
    Event findEventByCreatedDate(Date createdDate);

    /**
     *  Looks for event by provided modified date
     *  @param modifiedDate - instance of util.Date that represents date and time when event was modified last time
     *  @return list of found Event instances
     **/
    List<Event> findByModifiedDate(Date modifiedDate);

    /**
     *  Creates new Account and returns it by provided User instance.
     *  @param event - instance of Event to be persisted
     *  @return created Account
     **/
    Event create(Event event);

    /**
     *  Updates a stored account and returns its updated variant.
     *  @param update - an updated variation of Account that must be persisted
     **/
    Event saveChanges(Event update);
    
    
    
    
    
}
