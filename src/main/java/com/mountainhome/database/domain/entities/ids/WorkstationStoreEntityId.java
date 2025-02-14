package com.mountainhome.database.domain.entities.ids;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WorkstationStoreEntityId  implements Serializable {
    private FortressEntity fortress;
    private WorkstationTypeEntity workstationType;
}
