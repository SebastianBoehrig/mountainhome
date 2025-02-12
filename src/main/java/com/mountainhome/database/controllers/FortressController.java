package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.FortressUpdateDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.mappers.FortressMapper;
import com.mountainhome.database.services.DwarfService;
import com.mountainhome.database.services.FortressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class FortressController {
    protected FortressMapper fortressMapper;
    private final FortressService fortressService;
    private final DwarfService dwarfService;

    public FortressController(FortressService fortressService, DwarfService dwarfService, FortressMapper fortressMapper) {
        this.fortressService = fortressService;
        this.dwarfService = dwarfService;
        this.fortressMapper = fortressMapper;
    }

    /*@GetMapping(path = "/fortress")
    public List<FortressDto> getFortresses() {
        List<FortressEntity> fortressEntityList = fortressService.getFortresses();
        return fortressEntityList.stream().map(fortressMapper::mapTo).toList();
    }

    @GetMapping(path = "/fortress/{id}")
    public ResponseEntity<FortressDto> getFortressById(@PathVariable("id") int id) {
        Optional<FortressEntity> fortressOptional = fortressService.getFortressById(id);
        return fortressOptional.map(fortressEntity -> {
            FortressDto fortressDto = fortressMapper.mapTo(fortressEntity);
            return new ResponseEntity<>(fortressDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/fortresses/name/{name}")
    public List<FortressDto> getFortressesByName(@PathVariable("name") String name) {
        List<FortressEntity> fortressEntityList = fortressService.getFortressesByName(name);
        return fortressEntityList.stream().map(fortressMapper::mapTo).toList();
    }*/

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

    @PatchMapping(path = "/fortress/{name}")
    public ResponseEntity<FortressDto> updateFortress(@PathVariable("name") String name, @RequestBody FortressUpdateDto updates) {
        // execute
        FortressEntity changedFortress = fortressService.updateFortress(name, updates.getKingId());
        // map n return
        return new ResponseEntity<>(fortressMapper.toFortressDto(changedFortress), HttpStatus.OK);
    }
}
