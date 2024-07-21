package com.mountainhome.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DwarfDto {
    private Integer id;
    private String name;
    private Short age;
    private Short heightInCm;
    private Integer partnerId;
    private Integer fortressId;
    private Map<String, Integer> jobSkill;
    private ResourceDto favoriteFood;
}
