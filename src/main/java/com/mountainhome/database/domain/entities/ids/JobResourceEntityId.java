package com.mountainhome.database.domain.entities.ids;

import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.ResourceEntity;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class JobResourceEntityId implements Serializable {
    private ResourceEntity resource;
    private JobEntity job;
}
