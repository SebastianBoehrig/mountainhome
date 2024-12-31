package com.mountainhome.database.domain.entities;

import com.mountainhome.database.domain.entities.ids.WorkstationSkillEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workstation_skill")
@IdClass(WorkstationSkillEntityId.class)
public class WorkstationSkillEntity {
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private WorkstationTypeEntity workstationType;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private DwarfEntity dwarf;

    private Integer level;
}
