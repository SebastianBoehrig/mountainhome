package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.WorldStateEntity;
import com.mountainhome.database.repositories.WorldStateRepository;
import com.mountainhome.database.services.WorldStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class WorldStateServiceImpl implements WorldStateService {
    private final WorldStateRepository worldStateRepository;

    private static final int WORLD_STATE_ID = 1;

    public WorldStateServiceImpl(WorldStateRepository worldStateRepository) {
        this.worldStateRepository = worldStateRepository;
    }

    @Override
    public WorldStateEntity getWorldState() {
        return worldStateRepository.findById(WORLD_STATE_ID)
                .orElseThrow(() -> {
                    log.error("Could not find the internal worldstate");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not find the internal worldstate");
                });
    }
}
