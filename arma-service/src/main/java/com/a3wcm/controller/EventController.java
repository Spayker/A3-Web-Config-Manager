package com.a3wcm.controller;

import com.a3wcm.domain.Event;
import com.a3wcm.dto.EventDto;
import com.a3wcm.dto.mapper.EventMapper;
import com.a3wcm.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 *  A controller layer with all needed (for now) methods.
 *  Last ones are called when request handling starts happening.
 *  Requests come on correspond url that linked by RequestMapping annotation with an appropriate declared method below.
 **/
@RestController
@RequestMapping("/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> foundEvent = eventService.findEventById(id);
        return foundEvent.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventDto eventDto) {
        Event event = eventMapper.eventDtoToEvent(eventDto);
        return new ResponseEntity<>(eventService.create(event), HttpStatus.CREATED);
    }

}
