package com.mountainhome.database.domain.entities;

import com.mountainhome.database.domain.entities.ids.WorkstationStoreEntityId;
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
@Table(name = "workstation_store")
@IdClass(WorkstationStoreEntityId.class)
public class WorkstationStoreEntity {
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private FortressEntity fortress;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private WorkstationTypeEntity workstationType;

    @JoinColumn(nullable = false)
    private Integer amount;
}
