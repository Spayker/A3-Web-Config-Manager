package com.a3wcm.controller;

import com.a3wcm.domain.Event;
import com.a3wcm.dto.mapper.EventMapper;
import com.a3wcm.service.EventService;
import com.a3wcm.util.factory.EventFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Mock
    private EventMapper eventMapper;

    private MockMvc mockMvc;

    private static final String BASE_PATH = "/v1/events/";

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    private static Stream<Arguments> provideCommonEvents() {
        return Stream.of(
                Arguments.of(EventFactory.createEvent(1L,"name1", "description1", new Date(), null)),
                Arguments.of(EventFactory.createEvent(2L,"name2", "description2", new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Looks for event by id")
    public void shouldGetEventById(Event event) throws Exception {
        when(eventService.findEventById(event.getId())).thenReturn(Optional.of(event));

        mockMvc.perform(get(BASE_PATH + event.getId()))
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.name").value(event.getName()))
                .andExpect(jsonPath("$.description").value(event.getDescription()))
                .andExpect(jsonPath("$.createdDate").value(event.getCreatedDate()))
                .andExpect(jsonPath("$.modifiedDate").value(event.getModifiedDate()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideCommonEvents")
    @DisplayName("Creates a new event and returns created event")
    public void shouldRegisterNewEvent(Event event) throws Exception {
        String json = mapper.writeValueAsString(event);

        when(eventService.create(any())).thenReturn(event);
        mockMvc.perform(post(BASE_PATH)
                .principal(new UserPrincipal("test"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.name").value(event.getName()))
                .andExpect(jsonPath("$.description").value(event.getDescription()))
                .andExpect(jsonPath("$.createdDate").value(event.getCreatedDate()))
                .andExpect(jsonPath("$.modifiedDate").value(event.getModifiedDate()))
                .andExpect(status().isCreated());
    }

}
