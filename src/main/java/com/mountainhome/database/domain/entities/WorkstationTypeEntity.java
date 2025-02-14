package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "workstation_type")
public class WorkstationTypeEntity {
    @Id
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workstationType")
    private List<JobEntity> jobs;
}
