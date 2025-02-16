package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.FortressUpdateDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.mappers.FortressMapper;
import com.mountainhome.database.services.FortressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class FortressController {
    protected FortressMapper fortressMapper;
    private final FortressService fortressService;

    public FortressController(FortressService fortressService, FortressMapper fortressMapper) {
        this.fortressService = fortressService;
        this.fortressMapper = fortressMapper;
    }

    @GetMapping(path = "/fortress")
    public List<String> getFortressList() {
        return fortressService.getFortressNames();
    }

    @GetMapping(path = "/fortress/{name}")
    public ResponseEntity<FortressDto> getFortress(@PathVariable("name") String name) {
        // execute
        FortressEntity fortress = fortressService.getFortress(name);
        // map n return
        return new ResponseEntity<>(fortressMapper.toFortressDto(fortress), HttpStatus.OK);
    }

    @PostMapping(path = "/fortress")
    public ResponseEntity<FortressDto> createFortress(@RequestBody FortressDto fortress) {
        // pre-filter
        if (fortress.getName() == null) {
            log.error("got a createFortress request without a name"); //TODO: if i use this more often, make it into something easier
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every dwarven fortress has a name!");
        }
        // map
        FortressEntity fortressEntity = fortressMapper.toFortressEntity(fortress);
        // execute
        FortressEntity createdFortress = fortressService.createFortress(fortressEntity, fortress.getKingId());
        // map n return
        return new ResponseEntity<>(fortressMapper.toFortressDto(createdFortress), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/fortress/{fortress_name}")
    public ResponseEntity<FortressDto> updateFortress(@PathVariable("fortress_name") String name, @RequestBody FortressUpdateDto updates) {
        // execute
        FortressEntity changedFortress = fortressService.updateFortress(name, updates.getKingId());
        // map n return
        return new ResponseEntity<>(fortressMapper.toFortressDto(changedFortress), HttpStatus.OK);
    }
}
