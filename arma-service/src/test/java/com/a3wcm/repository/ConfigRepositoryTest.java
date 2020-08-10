package com.a3wcm.repository;

import com.a3wcm.domain.Config;
import com.a3wcm.util.factory.ConfigFactory;
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
public class ConfigRepositoryTest {

    @Autowired
    private ConfigRepository repository;

    @AfterEach
    public void clearRecordsInDb(){
        repository.deleteAll();
    }

    private static Stream<Arguments> provideCommonConfigs() {
        return Stream.of(
                Arguments.of(ConfigFactory.createConfig(1L,"name1", "note1", INFANTRY, new Date(), null)),
                Arguments.of(ConfigFactory.createConfig(2L,"name2", "note2", VEHICLE, new Date(), null))
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Saves configs by repository API")
    public void shouldSaveConfig(Config config){
        // when
        Config savedConfig = repository.saveAndFlush(config);

        // then
        assertNotNull(savedConfig);
        assertEquals(config.getCreatedDate(), savedConfig.getCreatedDate());
        assertEquals(config.getModifiedDate(), savedConfig.getModifiedDate());
        assertEquals(config.getName(), savedConfig.getName());
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Returns saved configs by their ids")
    public void shouldFindConfigById(Config config) {
        // when
        Config savedConfig = repository.saveAndFlush(config);
        Optional<Config> foundEntity = repository.findById(config.getId());

        // then
        assertNotNull(savedConfig);
        assertTrue(foundEntity.isPresent());
        Config foundConfig = foundEntity.get();
        assertEquals(config.getCreatedDate(), foundConfig.getCreatedDate());
        assertEquals(config.getModifiedDate(), foundConfig.getModifiedDate());
        assertEquals(config.getName(), foundConfig.getName());
        assertEquals(config.getType(), foundConfig.getType());
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Returns saved configs by their names")
    public void shouldFindConfigByName(Config config) {
        // given
        final int expectedFoundConfigs = 1;

        // when
        repository.saveAndFlush(config);
        List<Config> foundConfigs = repository.findByNameContains(config.getName());

        // then
        assertNotNull(foundConfigs);
        assertEquals(expectedFoundConfigs, foundConfigs.size());
        Config foundConfig = foundConfigs.get(0);
        assertEquals(config.getCreatedDate(), foundConfig.getCreatedDate());
        assertEquals(config.getModifiedDate(), foundConfig.getModifiedDate());
        assertEquals(config.getName(), foundConfig.getName());
        assertEquals(config.getType(), foundConfig.getType());
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
    @DisplayName("Returns saved configs by their names")
    public void shouldFindConfigsByName(List<Config> configs) {
        // given
        final String name = "name";

        // when
        configs.forEach(repository::saveAndFlush);
        List<Config> foundConfigs = repository.findByNameContains(name);

        // then
        assertNotNull(foundConfigs);
        assertEquals(configs.size(), foundConfigs.size());

        for(int i = 0; i != configs.size(); i++){
            assertEquals(configs.get(i).getName(), 		foundConfigs.get(i).getName());
            assertEquals(configs.get(i).getType(), 		foundConfigs.get(i).getType());
            assertEquals(configs.get(i).getCreatedDate(),  foundConfigs.get(i).getCreatedDate());
            assertEquals(configs.get(i).getModifiedDate(), foundConfigs.get(i).getModifiedDate());
        }
    }

    @ParameterizedTest
    @MethodSource("provideCommonConfigs")
    @DisplayName("Returns saved configs by their type")
    public void shouldFindConfigByType(Config config) {
        // given
        // when
        repository.saveAndFlush(config);
        List<Config> foundConfigs = repository.findByType(config.getType());

        // then
        assertNotNull(foundConfigs);
        assertFalse(foundConfigs.isEmpty());

        foundConfigs.forEach(foundConfig -> {
            assertEquals(config.getCreatedDate(), 	foundConfig.getCreatedDate());
            assertEquals(config.getModifiedDate(),  foundConfig.getModifiedDate());
            assertEquals(config.getName(), 		    foundConfig.getName());
            assertEquals(config.getType(), 		    foundConfig.getType());
        });
    }

    private static Stream<Arguments> provideSameModifiedDateConfigList() {
        final Date modifiedDate = new Date();
        List<Config> configList = List.of(
                ConfigFactory.createConfig("name1", "note1", INFANTRY, new Date(), modifiedDate),
                ConfigFactory.createConfig("name2", "note2", VEHICLE, new Date(), modifiedDate));
        return Stream.of(Arguments.of(configList));
    }

    @ParameterizedTest
    @MethodSource("provideSameModifiedDateConfigList")
    @DisplayName("Returns saved configs by their modified date")
    public void shouldFindConfigByModifiedDate(List<Config> configs) {
        // given
        final Date expectedModifiedDate = configs.get(0).getModifiedDate();

        // when
        configs.forEach(repository::save);
        List<Config> foundConfigs = repository.findByModifiedDate(expectedModifiedDate);

        // then
        assertNotNull(foundConfigs);
        assertEquals(configs.size(), foundConfigs.size());

        for(int i = 0; i != configs.size(); i++){
            assertEquals(configs.get(i).getName(), 		foundConfigs.get(i).getName());
            assertEquals(configs.get(i).getType(), 		foundConfigs.get(i).getType());
            assertEquals(configs.get(i).getCreatedDate(),  foundConfigs.get(i).getCreatedDate());
            assertEquals(configs.get(i).getModifiedDate(), foundConfigs.get(i).getModifiedDate());
        }
    }

}
