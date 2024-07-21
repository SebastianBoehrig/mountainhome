package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.services.DwarfService;
import com.mountainhome.database.services.FortressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DwarfController {
    private final DwarfService dwarfService;
    private final FortressService fortressService;
    private final Mapper<DwarfEntity, DwarfDto> dwarfMapper;

    public DwarfController(DwarfService dwarfService, FortressService fortressService, Mapper<DwarfEntity, DwarfDto> dwarfMapper) {
        this.dwarfService = dwarfService;
        this.fortressService = fortressService;
        this.dwarfMapper = dwarfMapper;
    }

    @GetMapping(path = "/dwarves")
    public List<DwarfDto> getDwarves() {
        List<DwarfEntity> dwarfEntityList = dwarfService.getDwarves();
        return dwarfEntityList.stream().map(dwarfMapper::mapTo).toList();
    }

    @GetMapping(path = "/dwarf/{id}")
    public ResponseEntity<DwarfDto> getDwarfById(@PathVariable("id") int id) {
        Optional<DwarfEntity> dwarfOptional = dwarfService.getDwarfById(id);
        return dwarfOptional.map(dwarfEntity -> {
            DwarfDto dwarfDto = dwarfMapper.mapTo(dwarfEntity);
            return new ResponseEntity<>(dwarfDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/dwarves/name/{name}")
    public List<DwarfDto> getDwarvesByName(@PathVariable("name") String name) {
        List<DwarfEntity> dwarfEntityList = dwarfService.getDwarvesByName(name);
        return dwarfEntityList.stream().map(dwarfMapper::mapTo).toList();
    }

    @PostMapping(path = "/dwarf")
    public ResponseEntity<DwarfDto> createDwarf(@RequestBody DwarfDto dwarfDto) {
        DwarfEntity dwarfEntity = dwarfMapper.mapFrom(dwarfDto);
        // TODO: check if dwarf has age>0
        // assign fav_food.
        DwarfEntity savedDwarf = dwarfService.createDwarf(dwarfEntity);
        return new ResponseEntity<>(dwarfMapper.mapTo(savedDwarf), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/dwarf/{id}")
    public ResponseEntity<DwarfDto> updateDwarf(@PathVariable("id") int id, @RequestBody DwarfDto dwarfDto) {
        DwarfEntity dwarfEntity = dwarfMapper.mapFrom(dwarfDto);
        DwarfEntity updatedDwarf = dwarfService.updateDwarf(id, dwarfEntity);
        return new ResponseEntity<>(dwarfMapper.mapTo(updatedDwarf), HttpStatus.OK);
    }

    @PatchMapping(path = "/dwarf/{id}/fortress/{fortressId}")
    public ResponseEntity<DwarfDto> migrateDwarf(@PathVariable("id") int id, @PathVariable("fortressId") Integer fortressId) {
        DwarfEntity updatedDwarf = dwarfService.migrateDwarf(id, fortressId);
        return new ResponseEntity<>(dwarfMapper.mapTo(updatedDwarf), HttpStatus.OK);
    }

    @PatchMapping(path = "/dwarf/{id}/partner/{partnerId}")
    public ResponseEntity<List<DwarfDto>> marryDwarves(@PathVariable("id") int id, @PathVariable("partnerId") Integer partnerId) {
        List<DwarfEntity> updatedDwarves = dwarfService.marryDwarves(id, partnerId);
        List<DwarfDto> dwarfDtoList = updatedDwarves.stream().map(dwarfMapper::mapTo).toList();
        return new ResponseEntity<>(dwarfDtoList, HttpStatus.OK);
    }
}
