package com.a3wcm.dto.mapper;

import com.a3wcm.domain.Event;
import com.a3wcm.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    Event eventDtoToEvent(EventDto eventDto);

}
