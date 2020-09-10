package com.a3wcm.util.factory;

import com.a3wcm.domain.Event;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class EventFactory {

    public static Event createDummyEvent(){
        return Event.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .createdDate(new Date())
                .modifiedDate(null)
                .build();
    }

    public static Event createEvent(String name,
                                      String description,
                                      Date createDate,
                                      Date modifiedDate) {
        return Event.builder()
                .name(name)
                .description(description)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .build();
    }

    public static Event createEvent(Long id,
                                      String name,
                                      String description,
                                      Date createDate,
                                      Date modifiedDate) {
        return Event.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdDate(createDate)
                .modifiedDate(modifiedDate)
                .build();
    }

}
