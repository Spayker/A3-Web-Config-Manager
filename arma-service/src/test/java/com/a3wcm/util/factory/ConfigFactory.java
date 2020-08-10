package com.a3wcm.util.factory;

import com.a3wcm.domain.Config;
import com.a3wcm.domain.ConfigType;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class ConfigFactory {

    public static Config createDummyConfig(){
        return Config.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .createdDate(new Date())
                .modifiedDate(null)
                .build();
    }

    public static Config createConfig(String name,
                                        String note,
                                        ConfigType configType,
                                        Date createDate,
                                        Date modifiedDate) {
        return Config.builder()
                .name(name)
                .note(note)
                .type(configType)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .build();
    }

    public static Config createConfig(Long id,
                                        String name,
                                        String note,
                                        ConfigType configType,
                                        Date createDate,
                                        Date modifiedDate) {
        return Config.builder()
                .id(id)
                .name(name)
                .note(note)
                .type(configType)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}
