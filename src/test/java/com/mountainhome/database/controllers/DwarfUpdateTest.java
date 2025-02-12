package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.dto.DwarfUpdateDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.helper.RestTemplateConfig;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
class DwarfUpdateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FortressRepository fortressRepository;
    @Autowired
    DwarfRepository dwarfRepository;
    private String url;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/dwarf/{dwarf_id}";
    }

    //TODO: fav food, workstation
    //TODO: gets

    @Test
    void updateDwarfBadDwarfTest() {
        // Given a fortress exists
        fortressRepository.save(FortressEntity.builder().name("Fort-1").build());
        // When I migrate a non-existing dwarf to the fortress
        DwarfUpdateDto body = DwarfUpdateDto.builder().fortress("Fort-1").build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This dwarf doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateDwarfMigrateDwarfTest() {
        // Given 2 Fortresses exist, and a dwarf exists in one of the fortresses
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        fortressRepository.save(FortressEntity.builder().name("Fort-2").build());
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        // When I migrate the dwarf to the other fortress
        DwarfUpdateDto body = DwarfUpdateDto.builder().fortress("Fort-2").build();
        ResponseEntity<DwarfDto> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DwarfDto.class, 1);
        // Then the dwarf is assigned to the fortress
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Fort-2", actualResponse.getBody().getFortress());
    }

    @Test
    void updateDwarfBadFortTest() {
        // Given a dwarf exists in a fortress
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        // When I migrate the dwarf to a non-existing fortress
        DwarfUpdateDto body = DwarfUpdateDto.builder().fortress("Fort-2").build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This fortress doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateDwarfRenameDwarfTest() {
        // a dwarf exists
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        dwarfRepository.save(DwarfEntity.builder().id(1).name("Dain").fortress(fort1).build());
        // When I update the dwarfs name
        DwarfUpdateDto body = DwarfUpdateDto.builder().name("Oin").build();
        ResponseEntity<DwarfDto> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DwarfDto.class, 1);
        // Then the dwarfs name is changed
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Oin", actualResponse.getBody().getName());
    }

    @Test
    void updateDwarfMarryDwarvesTest() {
        // Given 1 Fortresses exist, and 2 dwarves exists in the fortress
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        dwarfRepository.save(DwarfEntity.builder().id(2).fortress(fort1).build());
        // When I marry the dwarfs to another
        DwarfUpdateDto body = DwarfUpdateDto.builder().partnerId(2).build();
        ResponseEntity<DwarfDto> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DwarfDto.class, 1);
        // Then the dwarves are married
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(2, actualResponse.getBody().getPartnerId());

        Optional<DwarfEntity> dwarf2 = dwarfRepository.findById(2);
        assertTrue(dwarf2.isPresent());
        assertEquals(1, dwarf2.get().getPartner().getId());
    }

    @Test
    void updateDwarfMarryDwarvesDivorceTest() {
        // Given 1 Fortresses exist, and 4 dwarves exists in the fortress, married in 2 pairs
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        DwarfEntity dwarf1 = DwarfEntity.builder().fortress(fort1).build();
        DwarfEntity dwarf2 = DwarfEntity.builder().fortress(fort1).build();
        DwarfEntity dwarf3 = DwarfEntity.builder().fortress(fort1).build();
        DwarfEntity dwarf4 = DwarfEntity.builder().fortress(fort1).build();
        dwarf1.setPartner(dwarf2);
        dwarf2.setPartner(dwarf1);
        dwarf3.setPartner(dwarf4);
        dwarf4.setPartner(dwarf3);
        dwarfRepository.save(dwarf1);
        dwarfRepository.save(dwarf2);
        dwarfRepository.save(dwarf3);
        dwarfRepository.save(dwarf4);
        // When I marry 2 dwarves outside their current marriage
        DwarfUpdateDto body = DwarfUpdateDto.builder().partnerId(3).build();
        ResponseEntity<DwarfDto> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DwarfDto.class, 2);
        // Then the dwarves are married and the other 2 are divorced
        Optional<DwarfEntity> dwarf1Result = dwarfRepository.findById(1);
        assertTrue(dwarf1Result.isPresent());
        assertNull(dwarf1Result.get().getPartner());

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(3, actualResponse.getBody().getPartnerId());

        Optional<DwarfEntity> dwarf3Result = dwarfRepository.findById(3);
        assertTrue(dwarf3Result.isPresent());
        assertEquals(2, dwarf3Result.get().getPartner().getId());

        Optional<DwarfEntity> dwarf4Result = dwarfRepository.findById(4);
        assertTrue(dwarf4Result.isPresent());
        assertNull(dwarf4Result.get().getPartner());
    }

    @Test
    void updateDwarfMarryDwarvesBadPartnerTest() {
        // Given a Fortresses exists, and a dwarf exists in the fortress
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        // When I marry the dwarfs to a non-existing dwarf
        DwarfUpdateDto body = DwarfUpdateDto.builder().partnerId(2).build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This partner-dwarf doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateDwarfMarryDwarvesSelfTest() {
        // Given 1 Fortresses exist, and 1 dwarves exists in the fortress
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fort1);
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        // When I marry the dwarf to itself
        DwarfUpdateDto body = DwarfUpdateDto.builder().partnerId(1).build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("A dwarf can't marry itself!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateDwarfMarryDwarvesDifferentFortressTest() {
        // Given 1 Fortresses exist, and 1 dwarves exists in the fortress
        FortressEntity fort1 = FortressEntity.builder().name("Fort-1").build();
        FortressEntity fort2 = FortressEntity.builder().name("Fort-2").build();
        fortressRepository.save(fort1);
        fortressRepository.save(fort2);
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fort1).build());
        dwarfRepository.save(DwarfEntity.builder().id(2).fortress(fort2).build());
        // When try to marry the dwarves
        DwarfUpdateDto body = DwarfUpdateDto.builder().partnerId(2).build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, 1);
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("Dwarves must live together to marry!", actualResponse.getBody().getMessage());
    }
}
