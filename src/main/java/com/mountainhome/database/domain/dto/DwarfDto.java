package com.mountainhome.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DwarfDto {
    private Integer id;
    private String name;
    private LocalDate birthday;
    private Short heightInCm;
    private Integer partnerId;
    private String fortress;
    private Map<String, Integer> workstationSkill;
    private ResourceDto favoriteFood;
}
