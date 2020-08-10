package com.a3wcm.controller;

import com.a3wcm.domain.Config;
import com.a3wcm.dto.mapper.ConfigMapper;
import com.a3wcm.service.ConfigService;
import com.a3wcm.util.factory.ConfigFactory;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.a3wcm.domain.ConfigType.INFANTRY;
import static com.a3wcm.domain.ConfigType.VEHICLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConfigControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private ConfigController configController;

    @Mock
    private ConfigService configService;

    @Mock
    private ConfigMapper configMapper;

    private MockMvc mockMvc;

    private static final String BASE_PATH = "/v1/configs/";

    @BeforeEach
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(configController).build();
    }

    private static Stream<Arguments> provideCommonConfigs() {
        return Stream.of(
                Arguments.of(ConfigFactory.createConfig(1L,"name1", "note1", INFANTRY, new Date(), null)),
                Arguments.of(ConfigFactory.createConfig(2L,"name2", "note2", VEHICLE, new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by id")
    public void shouldGetConfigById(Config config) throws Exception {
        when(configService.findConfigById(config.getId())).thenReturn(Optional.of(config));

        mockMvc.perform(get(BASE_PATH + config.getId()))
                .andExpect(jsonPath("$.id").value(config.getId()))
                .andExpect(jsonPath("$.name").value(config.getName()))
                .andExpect(jsonPath("$.note").value(config.getNote()))
                .andExpect(jsonPath("$.type").value(config.getType().toString()))
                .andExpect(jsonPath("$.createdDate").value(config.getCreatedDate()))
                .andExpect(jsonPath("$.modifiedDate").value(config.getModifiedDate()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Creates a new config and returns created config")
    public void shouldRegisterNewConfig(Config config) throws Exception {
        String json = mapper.writeValueAsString(config);

        when(configService.create(any())).thenReturn(config);
        mockMvc.perform(post(BASE_PATH)
                .principal(new UserPrincipal("test"))
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.id").value(config.getId()))
                .andExpect(jsonPath("$.name").value(config.getName()))
                .andExpect(jsonPath("$.note").value(config.getNote()))
                .andExpect(jsonPath("$.type").value(config.getType().toString()))
                .andExpect(jsonPath("$.createdDate").value(config.getCreatedDate()))
                .andExpect(jsonPath("$.modifiedDate").value(config.getModifiedDate()))
                .andExpect(status().isCreated());
    }





}
