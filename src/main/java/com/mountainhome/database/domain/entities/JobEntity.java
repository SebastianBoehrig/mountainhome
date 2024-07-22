package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "job")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private WorkstationTypeEntity workstationType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<JobInputEntity> inputs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<JobProductEntity> products;
}
