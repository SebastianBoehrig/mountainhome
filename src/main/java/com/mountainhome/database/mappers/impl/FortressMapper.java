/*
package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.ResourceStoreEntity;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.repositories.DwarfRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FortressMapper implements Mapper<FortressEntity, FortressDto> {
    private final DwarfRepository dwarfRepository;
    private final ModelMapper modelMapper;

    public FortressMapper(ModelMapper modelMapper, DwarfRepository dwarfRepository) {
        this.modelMapper = modelMapper;
        this.dwarfRepository = dwarfRepository;
        this.modelMapper.addMappings(getPropertyMapFrom());
        this.modelMapper.addMappings(getPropertyMapTo());
    }

    @Override
    public FortressDto mapTo(FortressEntity fortressEntity) {
        return modelMapper.map(fortressEntity, FortressDto.class);
    }

    @Override
    public FortressEntity mapFrom(FortressDto fortressDto) {
        return modelMapper.map(fortressDto, FortressEntity.class);
    }

    private PropertyMap<FortressEntity, FortressDto> getPropertyMapTo() {
        Converter<List<WorkstationEntity>, Map<String, Integer>> convertWorkstationsToCountMap = this::setWorkstationCountMap;
        Converter<List<ResourceStoreEntity>, Map<String, Integer>> convertResourceStoresToValueMap = this::setResourceStoreValueMap;
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                // Note: this is not normal code. It is "EDSL" so don't get confused
                // convert real Objects into id's
                using(convertWorkstationsToCountMap).map(source.getWorkstations()).setWorkstationCount(null);
                using(convertResourceStoresToValueMap).map(source.getResourceStores()).setResourceStores(null);
            }
        };
    }

    private Map<String, Integer> setWorkstationCountMap(MappingContext<List<WorkstationEntity>, Map<String, Integer>> context) {
        List<WorkstationEntity> workstationEntityList = context.getSource();
        if (workstationEntityList == null) return null;
        return workstationEntityList.stream().collect(Collectors.toMap(ws -> ws.getWorkstationType().getName(), ws -> 1, Integer::sum));
    }

    private Map<String, Integer> setResourceStoreValueMap(MappingContext<List<ResourceStoreEntity>, Map<String, Integer>> context) {
        List<ResourceStoreEntity> resourceStoreEntityList = context.getSource();
        if (resourceStoreEntityList == null) return null;

        Map<String, Integer> resourceStoreValueMap = new HashMap<>();
        resourceStoreEntityList.forEach(resourceStoreEntity -> resourceStoreValueMap.put(resourceStoreEntity.getResource().getName(), resourceStoreEntity.getItemCount()));
        return resourceStoreValueMap;
    }

    private PropertyMap<FortressDto, FortressEntity> getPropertyMapFrom() {
        Converter<Integer, DwarfEntity> convertIdToDwarfEntity = this::setKing;
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                // Note: this is not normal code. It is "EDSL" so don't get confused
                // ignore nested Properties that the user shouldn't be able to set
                skip(destination.getWorkstations());
                skip(destination.getResourceStores());
                // convert id's into real Objects
                using(convertIdToDwarfEntity).map(source.getKingId()).setKing(null);
            }
        };
    }

    private DwarfEntity setKing(MappingContext<Integer, DwarfEntity> context) {
        Integer id = context.getSource();
        if (id == null) return null;
        return dwarfRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "King doesn't exist!"));
    }
}
*/
