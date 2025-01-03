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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void migrateDwarfTest() {
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
    void migrateDwarfBadFortTest() {
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
    void migrateDwarfBadDwarfTest() {
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
}
