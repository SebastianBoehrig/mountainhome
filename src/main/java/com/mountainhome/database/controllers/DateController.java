package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DateDto;
import com.mountainhome.database.domain.entities.WorldStateEntity;
import com.mountainhome.database.mappers.DateMapper;
import com.mountainhome.database.services.WorldStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DateController {
    private final DateMapper dateMapper;
    private final WorldStateService worldStateService;

    public DateController(DateMapper dateMapper, WorldStateService worldStateService) {
        this.dateMapper = dateMapper;
        this.worldStateService = worldStateService;
    }

    @GetMapping(path="/date")
    public DateDto getDate() {
        // execute
        WorldStateEntity worldState = worldStateService.getWorldState();
        // map n return
        return dateMapper.toDateDto(worldState);
    }
}
