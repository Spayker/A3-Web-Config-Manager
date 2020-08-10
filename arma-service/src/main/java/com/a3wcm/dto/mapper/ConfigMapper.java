package com.a3wcm.dto.mapper;

import com.a3wcm.domain.Config;
import com.a3wcm.dto.ConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfigMapper {

    Config configDtoToConfig(ConfigDto configDto);

}
