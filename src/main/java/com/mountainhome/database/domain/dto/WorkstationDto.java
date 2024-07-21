package com.mountainhome.database.domain.dto;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkstationDto {
    private Integer id;
    private FortressEntity fortress;
    private WorkstationTypeEntity workstationType;
}
