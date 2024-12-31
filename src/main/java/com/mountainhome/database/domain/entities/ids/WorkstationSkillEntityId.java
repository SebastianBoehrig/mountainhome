package com.mountainhome.database.domain.entities.ids;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WorkstationSkillEntityId implements Serializable {
    private DwarfEntity dwarf;
    private WorkstationTypeEntity workstationType;
}
