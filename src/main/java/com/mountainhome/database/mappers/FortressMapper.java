package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FortressMapper {
    @Mapping(target = "creationYear", ignore = true) // To set this manually in the service
    @Mapping(target = "king", ignore = true) // To set this manually in the service
    @Mapping(target = "workstations", ignore = true)
    @Mapping(target = "resourceStores", ignore = true)
    FortressEntity toFortressEntity(FortressDto source);

    @Mapping(source = "king.id", target = "kingId")
    FortressDto toFortressDto(FortressEntity source);
}
