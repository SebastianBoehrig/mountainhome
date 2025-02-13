package com.mountainhome.database.domain.entities;

import com.mountainhome.database.domain.entities.ids.JobResourceEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@IdClass(JobResourceEntityId.class)
@AllArgsConstructor
@NoArgsConstructor
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
