package com.mountainhome.database.domain.dto;

import com.mountainhome.database.domain.entities.TestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    private Integer id;
    private String name;
    private Integer innerId;
}
