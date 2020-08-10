package com.a3wcm.service;

import com.a3wcm.client.AuthServiceClient;
import com.a3wcm.domain.Config;
import com.a3wcm.dto.mapper.ConfigMapper;
import com.a3wcm.repository.ConfigRepository;
import com.a3wcm.util.factory.ConfigFactory;
import org.apache.commons.lang.RandomStringUtils;
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
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.a3wcm.domain.ConfigType.INFANTRY;
import static com.a3wcm.domain.ConfigType.VEHICLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfigServiceTest {

    @InjectMocks
    private ConfigServiceImpl configService;

    @Mock
    private ConfigRepository repository;

    @Mock
    private ConfigMapper configMapper;

    @Mock
    private AuthServiceClient authClient;

    @BeforeEach
    public void setup() { initMocks(this); }

    @AfterEach
    public void clearDb() { repository.deleteAll(); }

    private static Stream<Arguments> provideCommonConfigs() {
        return Stream.of(
                Arguments.of(ConfigFactory.createConfig(1L,"name1", "note1", INFANTRY, new Date(), null)),
                Arguments.of(ConfigFactory.createConfig(2L,"name2", "note2", VEHICLE, new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Saves config by given config data")
    public void shouldCreateConfig(Config config) {
        // given
        final int expectedMethodInvokeTimes = 1;

        // when
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);

        // then
        assertNotNull(savedConfig);
        assertEquals(config.getName(), savedConfig.getName());

        assertNotNull(savedConfig.getCreatedDate());
        assertNull(savedConfig.getModifiedDate());

        verify(repository, times(expectedMethodInvokeTimes)).saveAndFlush(config);
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Updates config by received changes")
    public void shouldSaveChangesWhenUpdatedConfigGiven(Config config) {
        // given
        final int expectedMethodInvokeTimes = 2;
        final String updatePrefix = "_updated";

        // when
        when(repository.findByNameContains(config.getName())).thenReturn(null);
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config createdConfig = configService.create(config);

        createdConfig.setName(createdConfig.getName() + updatePrefix);

        when(repository.findByNameContains(createdConfig.getName())).thenReturn(List.of(createdConfig));
        when(repository.existsById(createdConfig.getId())).thenReturn(true);
        Config updatedConfig = configService.saveChanges(createdConfig);

        // then
        assertNotNull(updatedConfig);
        assertEquals(updatedConfig.getName(), 	        createdConfig.getName());
        assertEquals(updatedConfig.getCreatedDate(), 	createdConfig.getCreatedDate());
        assertEquals(updatedConfig.getModifiedDate(),   createdConfig.getModifiedDate());
        assertEquals(updatedConfig.getType(),           createdConfig.getType());

        verify(repository, times(expectedMethodInvokeTimes)).saveAndFlush(config);
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by name")
    public void shouldFindConfigByName(Config config) {
        // given
        final int expectedFoundConfigs = 1;

        // when
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);
        when(repository.findByNameContains(config.getName())).thenReturn(List.of(savedConfig));
        List<Config> foundConfigs = configService.findConfigByName(config.getName());

        assertEquals(expectedFoundConfigs, foundConfigs.size());
        assertEquals(foundConfigs.get(0).getName(), savedConfig.getName());
        assertEquals(foundConfigs.get(0).getNote(), savedConfig.getNote());
        assertEquals(foundConfigs.get(0).getType(), savedConfig.getType());
        assertEquals(foundConfigs.get(0).getCreatedDate(), savedConfig.getCreatedDate());
        assertEquals(foundConfigs.get(0).getModifiedDate(), savedConfig.getModifiedDate());
    }

    private static Stream<Arguments> provideSameNameConfigList() {
        final String name = "name";
        List<Config> configList = List.of(
                ConfigFactory.createConfig(name, "note1", INFANTRY, new Date(), null),
                ConfigFactory.createConfig(name, "note2", VEHICLE, new Date(), null));
        return Stream.of(Arguments.of(configList));
    }

    @ParameterizedTest
    @MethodSource("provideSameNameConfigList")
    @DisplayName("Looks for configs by name")
    public void shouldFindConfigByName(List<Config> configs) {
        // given
        final int expectedFoundConfigs = 2;

        // when
        when(repository.findByNameContains(configs.get(0).getName())).thenReturn(configs);

        List<Config> foundConfigs = configService.findConfigByName(configs.get(0).getName());

        assertEquals(expectedFoundConfigs, foundConfigs.size());
        assertEquals(foundConfigs.get(0).getName(), configs.get(0).getName());
        assertEquals(foundConfigs.get(1).getName(), configs.get(1).getName());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for config by empty string name")
    public void shouldFailWhenFindConfigByNameWithEmptyNameValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> configService.findConfigByName(""));
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by note")
    public void shouldFindConfigByNote(Config config) {
        // given
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);

        // when
        when(repository.findByNoteContains(config.getNote())).thenReturn(List.of(savedConfig));
        List<Config> foundConfigs = configService.findConfigByNote(config.getNote());

        assertNotNull(foundConfigs);
        assertFalse(foundConfigs.isEmpty());

        foundConfigs.forEach(foundConfig -> {
            assertEquals(foundConfig.getName(), savedConfig.getName());
            assertEquals(foundConfig.getNote(), savedConfig.getNote());
            assertEquals(foundConfig.getType(), savedConfig.getType());
            assertEquals(foundConfig.getCreatedDate(), savedConfig.getCreatedDate());
            assertEquals(foundConfig.getModifiedDate(), savedConfig.getModifiedDate());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for config by empty string note")
    public void shouldFailWhenFindConfigByEmailWithEmptyNoteValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> configService.findConfigByNote(""));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when looks for config by blank string note")
    public void shouldFailWhenFindConfigByEmailWithBlankNoteValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> configService.findConfigByNote("     "));
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by created date")
    public void shouldFindConfigByCreatedDate(Config config) {
        // given
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);

        // when
        when(repository.findByCreatedDate(config.getCreatedDate())).thenReturn(savedConfig);
        Config foundConfig = configService.findConfigByCreatedDate(config.getCreatedDate());

        assertNotNull(foundConfig);

        assertEquals(foundConfig.getName(), savedConfig.getName());
        assertEquals(foundConfig.getNote(), savedConfig.getNote());
        assertEquals(foundConfig.getType(), savedConfig.getType());
        assertEquals(foundConfig.getCreatedDate(), savedConfig.getCreatedDate());
        assertEquals(foundConfig.getModifiedDate(), savedConfig.getModifiedDate());
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by modified date")
    public void shouldFindConfigByModifiedDate(Config config) {
        // given
        final int expectedFoundConfigs = 1;
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);

        // when
        when(repository.findByModifiedDate(config.getModifiedDate())).thenReturn(List.of(savedConfig));
        List<Config> foundConfigs = configService.findByModifiedDate(config.getModifiedDate());

        assertNotNull(foundConfigs);
        assertEquals(expectedFoundConfigs, foundConfigs.size());

        foundConfigs.forEach(foundConfig -> {
            assertEquals(foundConfig.getName(), savedConfig.getName());
            assertEquals(foundConfig.getNote(), savedConfig.getNote());
            assertEquals(foundConfig.getType(), savedConfig.getType());
            assertEquals(foundConfig.getCreatedDate(), savedConfig.getCreatedDate());
            assertEquals(foundConfig.getModifiedDate(), savedConfig.getModifiedDate());
        });
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Looks for config by type")
    public void shouldFindConfigByType(Config config) {
        // given
        final int expectedFoundConfigs = 1;
        when(repository.saveAndFlush(config)).thenReturn(config);
        Config savedConfig = configService.create(config);

        // when
        when(repository.findByType(config.getType())).thenReturn(List.of(savedConfig));
        List<Config> foundConfigs = configService.findConfigByType(config.getType());

        assertNotNull(foundConfigs);
        assertEquals(expectedFoundConfigs, foundConfigs.size());

        foundConfigs.forEach(foundConfig -> {
            assertEquals(foundConfig.getName(), savedConfig.getName());
            assertEquals(foundConfig.getNote(), savedConfig.getNote());
            assertEquals(foundConfig.getType(), savedConfig.getType());
            assertEquals(foundConfig.getCreatedDate(), savedConfig.getCreatedDate());
            assertEquals(foundConfig.getModifiedDate(), savedConfig.getModifiedDate());
        });
    }

}
