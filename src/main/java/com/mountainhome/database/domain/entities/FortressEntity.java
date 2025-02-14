package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fortress")
public class FortressEntity {
    @Id
    private String name;
    private Integer creationYear;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private DwarfEntity king;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fortress")
    private List<WorkstationStoreEntity> workstations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fortress")
    private List<ResourceStoreEntity> resourceStores;
}
