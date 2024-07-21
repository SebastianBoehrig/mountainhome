package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.services.DwarfService;
import com.mountainhome.database.services.FortressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FortressController {
    private final FortressService fortressService;
    private final DwarfService dwarfService;
    private final Mapper<FortressEntity, FortressDto> fortressMapper;

    public FortressController(FortressService fortressService, DwarfService dwarfService, Mapper<FortressEntity, FortressDto> fortressMapper) {
        this.fortressService = fortressService;
        this.dwarfService = dwarfService;
        this.fortressMapper = fortressMapper;
    }

    @GetMapping(path = "/fortress")
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
    }

    @PostMapping(path = "/fortress")
    public ResponseEntity<FortressDto> createFortress(@RequestBody FortressDto fortress) {
        FortressEntity fortressEntity = fortressMapper.mapFrom(fortress);
        FortressEntity savedFortress = fortressService.createFortress(fortressEntity);
        return new ResponseEntity<>(fortressMapper.mapTo(savedFortress), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/fortress/{id}")
    public ResponseEntity<FortressDto> updateFortress(@PathVariable("id") int id, @RequestBody FortressDto fortressDto) {
        // only for trivial arguments
        FortressEntity fortressEntity = fortressMapper.mapFrom(fortressDto);
        FortressEntity updatedFortress = fortressService.updateFortress(id, fortressEntity);
        return new ResponseEntity<>(fortressMapper.mapTo(updatedFortress), HttpStatus.OK);
    }

    @PatchMapping(path = "/fortress/{id}/king/{kingId}")
    public ResponseEntity<FortressDto> crownKing(@PathVariable("id") int id, @PathVariable("kingId") Integer kingId) {
        FortressEntity updatedFortress = fortressService.crownKing(id, kingId);
        return new ResponseEntity<>(fortressMapper.mapTo(updatedFortress), HttpStatus.OK);
    }
}
