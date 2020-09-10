package com.a3wcm.service;

import com.a3wcm.client.AuthServiceClient;
import com.a3wcm.domain.Event;
import com.a3wcm.domain.Event;
import com.a3wcm.dto.mapper.EventMapper;
import com.a3wcm.repository.EventRepository;
import com.a3wcm.util.factory.EventFactory;
import com.a3wcm.util.factory.EventFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository repository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private AuthServiceClient authClient;

    @BeforeEach
    public void setup() { initMocks(this); }

    @AfterEach
    public void clearDb() { repository.deleteAll(); }

    private static Stream<Arguments> provideCommonEvents() {
        return Stream.of(
                Arguments.of(EventFactory.createEvent(1L,"name1", "description1", new Date(), null)),
                Arguments.of(EventFactory.createEvent(2L,"name2", "description2", new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Saves event by given event data")
    public void shouldCreateEvent(Event event) {
        // given
        final int expectedMethodInvokeTimes = 1;

        // when
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event savedEvent = eventService.create(event);

        // then
        assertNotNull(savedEvent);
        assertEquals(event.getName(), savedEvent.getName());

        assertNotNull(savedEvent.getCreatedDate());
        assertNull(savedEvent.getModifiedDate());

        verify(repository, times(expectedMethodInvokeTimes)).saveAndFlush(event);
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Updates event by received changes")
    public void shouldSaveChangesWhenUpdatedEventGiven(Event event) {
        // given
        final int expectedMethodInvokeTimes = 2;
        final String updatePrefix = "_updated";

        // when
        when(repository.findByNameContains(event.getName())).thenReturn(null);
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event createdEvent = eventService.create(event);

        createdEvent.setName(createdEvent.getName() + updatePrefix);

        when(repository.findByNameContains(createdEvent.getName())).thenReturn(List.of(createdEvent));
        when(repository.existsById(createdEvent.getId())).thenReturn(true);
        Event updatedEvent = eventService.saveChanges(createdEvent);

        // then
        assertNotNull(updatedEvent);
        assertEquals(updatedEvent.getName(), 	        createdEvent.getName());
        assertEquals(updatedEvent.getCreatedDate(), 	createdEvent.getCreatedDate());
        assertEquals(updatedEvent.getModifiedDate(),   createdEvent.getModifiedDate());
        assertEquals(updatedEvent.getDescription(),           createdEvent.getDescription());

        verify(repository, times(expectedMethodInvokeTimes)).saveAndFlush(event);
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Looks for event by name")
    public void shouldFindEventByName(Event event) {
        // given
        final int expectedFoundEvents = 1;

        // when
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event savedEvent = eventService.create(event);
        when(repository.findByNameContains(event.getName())).thenReturn(List.of(savedEvent));
        List<Event> foundEvents = eventService.findEventByName(event.getName());

        assertEquals(expectedFoundEvents, foundEvents.size());
        assertEquals(foundEvents.get(0).getName(), savedEvent.getName());
        assertEquals(foundEvents.get(0).getDescription(), savedEvent.getDescription());
        assertEquals(foundEvents.get(0).getCreatedDate(), savedEvent.getCreatedDate());
        assertEquals(foundEvents.get(0).getModifiedDate(), savedEvent.getModifiedDate());
    }

    private static Stream<Arguments> provideSameNameEventList() {
        final String name = "name";
        List<Event> eventList = List.of(
                EventFactory.createEvent(name, "description1", new Date(), null),
                EventFactory.createEvent(name, "description2", new Date(), null));
        return Stream.of(Arguments.of(eventList));
    }

    @ParameterizedTest
    @MethodSource("provideSameNameEventList")
    @DisplayName("Looks for events by name")
    public void shouldFindEventByName(List<Event> events) {
        // given
        final int expectedFoundEvents = 2;

        // when
        when(repository.findByNameContains(events.get(0).getName())).thenReturn(events);

        List<Event> foundEvents = eventService.findEventByName(events.get(0).getName());

        assertEquals(expectedFoundEvents, foundEvents.size());
        assertEquals(foundEvents.get(0).getName(), events.get(0).getName());
        assertEquals(foundEvents.get(1).getName(), events.get(1).getName());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for event by empty string name")
    public void shouldFailWhenFindEventByNameWithEmptyNameValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.findEventByName(""));
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Looks for event by note")
    public void shouldFindEventByNote(Event event) {
        // given
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event savedEvent = eventService.create(event);

        // when
        when(repository.findByDescriptionContains(event.getDescription())).thenReturn(List.of(savedEvent));
        List<Event> foundEvents = eventService.findEventByDescription(event.getDescription());

        assertNotNull(foundEvents);
        assertFalse(foundEvents.isEmpty());

        foundEvents.forEach(foundEvent -> {
            assertEquals(foundEvent.getName(), savedEvent.getName());
            assertEquals(foundEvent.getDescription(), savedEvent.getDescription());
            assertEquals(foundEvent.getCreatedDate(), savedEvent.getCreatedDate());
            assertEquals(foundEvent.getModifiedDate(), savedEvent.getModifiedDate());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for event by empty string note")
    public void shouldFailWhenFindEventByEmailWithEmptyNoteValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.findEventByDescription(""));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for event by blank string note")
    public void shouldFailWhenFindEventByEmailWithBlankNoteValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventService.findEventByDescription("     "));
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Looks for event by created date")
    public void shouldFindEventByCreatedDate(Event event) {
        // given
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event savedEvent = eventService.create(event);

        // when
        when(repository.findByCreatedDate(event.getCreatedDate())).thenReturn(savedEvent);
        Event foundEvent = eventService.findEventByCreatedDate(event.getCreatedDate());

        assertNotNull(foundEvent);

        assertEquals(foundEvent.getName(), savedEvent.getName());
        assertEquals(foundEvent.getDescription(), savedEvent.getDescription());
        assertEquals(foundEvent.getCreatedDate(), savedEvent.getCreatedDate());
        assertEquals(foundEvent.getModifiedDate(), savedEvent.getModifiedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Looks for event by modified date")
    public void shouldFindEventByModifiedDate(Event event) {
        // given
        final int expectedFoundEvents = 1;
        when(repository.saveAndFlush(event)).thenReturn(event);
        Event savedEvent = eventService.create(event);

        // when
        when(repository.findByModifiedDate(event.getModifiedDate())).thenReturn(List.of(savedEvent));
        List<Event> foundEvents = eventService.findByModifiedDate(event.getModifiedDate());

        assertNotNull(foundEvents);
        assertEquals(expectedFoundEvents, foundEvents.size());

        foundEvents.forEach(foundEvent -> {
            assertEquals(foundEvent.getName(), savedEvent.getName());
            assertEquals(foundEvent.getDescription(), savedEvent.getDescription());
            assertEquals(foundEvent.getCreatedDate(), savedEvent.getCreatedDate());
            assertEquals(foundEvent.getModifiedDate(), savedEvent.getModifiedDate());
        });
    }
    
}
