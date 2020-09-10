package com.a3wcm.repository;

import com.a3wcm.domain.Config;
import com.a3wcm.domain.Event;
import com.a3wcm.util.factory.ConfigFactory;
import com.a3wcm.util.factory.EventFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.a3wcm.domain.ConfigType.INFANTRY;
import static com.a3wcm.domain.ConfigType.VEHICLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class EventRepositoryTest {

    @Autowired
    private EventRepository repository;

    @AfterEach
    public void clearRecordsInDb(){
        repository.deleteAll();
    }

    private static Stream<Arguments> provideCommonEvents() {
        return Stream.of(
                Arguments.of(EventFactory.createEvent(1L,"name1", "description1", new Date(), null)),
                Arguments.of(EventFactory.createEvent(2L,"name2", "description2", new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Saves configs by repository API")
    public void shouldSaveEvent(Event event){
        // when
        Event savedEvent = repository.saveAndFlush(event);

        // then
        assertNotNull(savedEvent);
        assertEquals(event.getCreatedDate(), savedEvent.getCreatedDate());
        assertEquals(event.getModifiedDate(), savedEvent.getModifiedDate());
        assertEquals(event.getName(), savedEvent.getName());
        assertEquals(event.getDescription(), savedEvent.getDescription());
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Returns saved events by their ids")
    public void shouldFindEventById(Event event) {
        // when
        Event savedEvent = repository.saveAndFlush(event);
        Optional<Event> foundEntity = repository.findById(event.getId());

        // then
        assertNotNull(savedEvent);
        assertTrue(foundEntity.isPresent());
        Event foundConfig = foundEntity.get();
        assertEquals(event.getCreatedDate(), foundConfig.getCreatedDate());
        assertEquals(event.getModifiedDate(), foundConfig.getModifiedDate());
        assertEquals(event.getName(), foundConfig.getName());
        assertEquals(event.getDescription(), foundConfig.getDescription());
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Returns saved events by their names")
    public void shouldFindEventByName(Event event) {
        // given
        final int expectedFoundEvents = 1;

        // when
        repository.saveAndFlush(event);
        List<Event> foundEvents = repository.findByNameContains(event.getName());

        // then
        assertNotNull(foundEvents);
        assertEquals(expectedFoundEvents, foundEvents.size());
        Event foundConfig = foundEvents.get(0);
        assertEquals(event.getCreatedDate(), foundConfig.getCreatedDate());
        assertEquals(event.getModifiedDate(), foundConfig.getModifiedDate());
        assertEquals(event.getName(), foundConfig.getName());
        assertEquals(event.getDescription(), foundConfig.getDescription());
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
    @DisplayName("Returns saved configs by their names")
    public void shouldFindEventsByName(List<Event> events) {
        // given
        final String name = "name";

        // when
        events.forEach(repository::saveAndFlush);
        List<Event> foundEvents = repository.findByNameContains(name);

        // then
        assertNotNull(foundEvents);
        assertEquals(events.size(), foundEvents.size());

        for(int i = 0; i != events.size(); i++){
            assertEquals(events.get(i).getName(), 		  foundEvents.get(i).getName());
            assertEquals(events.get(i).getDescription(),  foundEvents.get(i).getDescription());
            assertEquals(events.get(i).getCreatedDate(),  foundEvents.get(i).getCreatedDate());
            assertEquals(events.get(i).getModifiedDate(), foundEvents.get(i).getModifiedDate());
        }
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Returns saved events by their description")
    public void shouldFindEventByDescription(Event event) {
        // given
        // when
        repository.saveAndFlush(event);
        List<Event> foundEvents = repository.findByDescriptionContains(event.getDescription());

        // then
        assertNotNull(foundEvents);
        assertFalse(foundEvents.isEmpty());

        foundEvents.forEach(foundConfig -> {
            assertEquals(event.getCreatedDate(), 	foundConfig.getCreatedDate());
            assertEquals(event.getModifiedDate(),   foundConfig.getModifiedDate());
            assertEquals(event.getName(), 		    foundConfig.getName());
            assertEquals(event.getDescription(), 	foundConfig.getDescription());
        });
    }

    private static Stream<Arguments> provideSameModifiedDateEventList() {
        final Date modifiedDate = new Date();
        List<Event> eventList = List.of(
                EventFactory.createEvent("name1", "description1", new Date(), modifiedDate),
                EventFactory.createEvent("name2", "description2", new Date(), modifiedDate));
        return Stream.of(Arguments.of(eventList));
    }

    @ParameterizedTest
    @MethodSource("provideSameModifiedDateEventList")
    @DisplayName("Returns saved events by their modified date")
    public void shouldFindEventByModifiedDate(List<Event> events) {
        // given
        final Date expectedModifiedDate = events.get(0).getModifiedDate();

        // when
        events.forEach(repository::save);
        List<Event> foundConfigs = repository.findByModifiedDate(expectedModifiedDate);

        // then
        assertNotNull(foundConfigs);
        assertEquals(events.size(), foundConfigs.size());

        for(int i = 0; i != events.size(); i++){
            assertEquals(events.get(i).getName(), 		  foundConfigs.get(i).getName());
            assertEquals(events.get(i).getDescription(),  foundConfigs.get(i).getDescription());
            assertEquals(events.get(i).getCreatedDate(),  foundConfigs.get(i).getCreatedDate());
            assertEquals(events.get(i).getModifiedDate(), foundConfigs.get(i).getModifiedDate());
        }
    }

}
