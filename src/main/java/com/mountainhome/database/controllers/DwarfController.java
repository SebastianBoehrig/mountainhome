package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.dto.DwarfUpdateDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.mappers.DwarfMapper;
import com.mountainhome.database.services.DwarfService;
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

    public DwarfController(DwarfService dwarfService, DwarfMapper dwarfMapper) {
        this.dwarfService = dwarfService;
        this.dwarfMapper = dwarfMapper;
    }

    @GetMapping(path = "/dwarf/{dwarf_id}")
    public ResponseEntity<DwarfDto> getDwarf(@PathVariable("dwarf_id") int id) {
        // execute
        DwarfEntity dwarf = dwarfService.getDwarf(id);
        // map n return
        return new ResponseEntity<>(dwarfMapper.toDwarfDto(dwarf), HttpStatus.OK);
    }

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
}
