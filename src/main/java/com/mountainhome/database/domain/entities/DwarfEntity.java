package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dwarf")
public class DwarfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate birthday;
    private Short heightInCm;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private DwarfEntity partner;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private FortressEntity fortress;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dwarf")
    private List<WorkstationSkillEntity> workstationSkill;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private ResourceEntity favoriteFood;
}
