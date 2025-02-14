package com.mountainhome.database.domain.entities;

import com.mountainhome.database.domain.entities.ids.JobResourceEntityId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
@IdClass(JobResourceEntityId.class)
public abstract class JobResourceEntity {
    @JoinColumn(nullable = false)
    private Integer amount;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private ResourceEntity resource;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private JobEntity job;
}
