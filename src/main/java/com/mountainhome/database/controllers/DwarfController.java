package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.dto.DwarfUpdateDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.mappers.DwarfMapper;
import com.mountainhome.database.services.DwarfService;
import com.mountainhome.database.services.FortressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class DwarfController {
    protected final DwarfMapper dwarfMapper;
    private final DwarfService dwarfService;
    private final FortressService fortressService;

    public DwarfController(DwarfService dwarfService, FortressService fortressService, DwarfMapper dwarfMapper) {
        this.dwarfService = dwarfService;
        this.fortressService = fortressService;
        this.dwarfMapper = dwarfMapper;
    }

    /*@GetMapping(path = "/dwarves")
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
    }*/

    /*@PostMapping(path = "/dwarf")
    public ResponseEntity<DwarfDto> createDwarf(@RequestBody DwarfDto dwarfDto) {
        DwarfEntity dwarfEntity = dwarfMapper.mapFrom(dwarfDto);
        // assign fav_food.
        DwarfEntity savedDwarf = dwarfService.createDwarf(dwarfEntity);
        return new ResponseEntity<>(dwarfMapper.mapTo(savedDwarf), HttpStatus.CREATED);
    }*/

    @PostMapping(path = "/dwarf")
    public ResponseEntity<DwarfDto> createDwarf(@RequestBody DwarfDto dwarf) {
        // pre-filter
        if (dwarf.getName() == null) {
            log.error("got a createDwarf request without a name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every dwarf has a name!");
        } else if (dwarf.getFortress() == null) {
            log.error("got a createDwarf request without a fortress");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every dwarf has a fortress!");
        }
        // map
        DwarfEntity dwarfEntity = dwarfMapper.toDwarfEntity(dwarf);
        // execute
        DwarfEntity createdDwarf = dwarfService.createDwarf(dwarfEntity, dwarf.getFortress());
        // map n return
        return new ResponseEntity<>(dwarfMapper.toDwarfDto(createdDwarf), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/dwarf/{dwarf_id}")
    public ResponseEntity<DwarfDto> updateDwarf(@PathVariable("dwarf_id") int dwarfId, @RequestBody DwarfUpdateDto updates){
        // execute
        DwarfEntity changedDwarf = dwarfService.updateDwarf(dwarfId, updates.getName(), updates.getPartnerId(), updates.getFortress());
        // map n return
        return new ResponseEntity<>(dwarfMapper.toDwarfDto(changedDwarf), HttpStatus.OK);
    }

    /*@PatchMapping(path = "/dwarf/{id}")
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
    }*/
}
