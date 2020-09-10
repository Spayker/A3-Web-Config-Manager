package com.a3wcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EventDto {

    private String name;

    private String description;

    private Date createdDate;

    private Date modifiedDate;

}
