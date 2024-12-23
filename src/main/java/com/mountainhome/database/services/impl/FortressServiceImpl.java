package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.ResourceStoreEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.ResourceRepository;
import com.mountainhome.database.services.FortressService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Log
@Service
public class FortressServiceImpl implements FortressService {
    private final FortressRepository fortressRepository;
    private final DwarfRepository dwarfRepository;
    private final ResourceRepository resourceRepository;

    public FortressServiceImpl(FortressRepository fortressRepository, DwarfRepository dwarfRepository, ResourceRepository resourceRepository) {
        this.fortressRepository = fortressRepository;
        this.dwarfRepository = dwarfRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public FortressEntity createFortress(FortressEntity fortressEntity) {
        List<ResourceStoreEntity> resourceStoreEntityList = new ArrayList<>();
        resourceRepository.findAll().forEach(
                resource -> resourceStoreEntityList.add(ResourceStoreEntity.builder().fortress(fortressEntity).resource(resource).itemCount(0).build())
        );
        fortressEntity.setResourceStores(resourceStoreEntityList);

        return fortressRepository.save(fortressEntity);
    }

    @Override
    public List<FortressEntity> getFortresses() {
        Iterable fortressEntityIterable = fortressRepository.findAll();
        Spliterator spliterator = fortressEntityIterable.spliterator();
        return StreamSupport.stream(spliterator, false).toList();
    }

    @Override
    public Optional<FortressEntity> getFortressById(int id) {
        return fortressRepository.findById(id);
    }

    @Override
    public List<FortressEntity> getFortressesByName(String name) {
        return fortressRepository.findByName(name);
    }

    @Override
    public FortressEntity updateFortress(int id, FortressEntity fortressEntity) {
        fortressEntity.setId(id);
        return fortressRepository.findById(id).map(existingFortress -> {
            Optional.ofNullable(fortressEntity.getName()).ifPresent(existingFortress::setName);
            Optional.ofNullable(fortressEntity.getCreationYear()).ifPresent(existingFortress::setCreationYear);
            return fortressRepository.save(existingFortress);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fortress does not exist"));
    }

    @Override
    public FortressEntity crownKing(int id, Integer kingId) {
        FortressEntity fortress = fortressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fortress does not exist"));

        if (kingId == null) {
            fortress.setKing(null);
            return fortressRepository.save(fortress);
        }
        DwarfEntity king = dwarfRepository.findById(kingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "King does not exist"));

        if (king.getFortress() != fortress) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "King needs to live in the fortress");
        }
        fortress.setKing(king);
        return fortressRepository.save(fortress);
    }
}
