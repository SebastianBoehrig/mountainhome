package com.mountainhome.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FortressDto {
    private String name;
    private Integer creationYear;
    private Integer kingId;
}
