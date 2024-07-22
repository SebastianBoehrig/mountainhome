package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class JobResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false)
    private Integer amount;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private ResourceEntity resource;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private JobEntity job;
}
