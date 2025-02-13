package com.mountainhome.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDto {
    private Integer id;
    private String name;
    private List<JobInputProductDto> inputs;
    private List<JobInputProductDto> products;
}
