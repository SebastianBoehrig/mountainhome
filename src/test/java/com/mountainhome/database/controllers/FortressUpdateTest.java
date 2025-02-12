package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.FortressUpdateDto;
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
class FortressUpdateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FortressRepository fortressRepository;
    @Autowired
    DwarfRepository dwarfRepository;
    private String url;

    @BeforeEach
    public void setUrl(@LocalServerPort int port) {
        url = "http://localhost:" + port + "/fortress/{name}";
    }

    @Test
    void updateFortressBadFortressTest() {
        // When I update a non-existing fortress
        FortressUpdateDto body = FortressUpdateDto.builder().build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, "Fort-1");
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This fortress doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateFortressCrownKingBadKingTest() {
        // Given a Fortress exists
        fortressRepository.save(FortressEntity.builder().name("Fort-1").build());
        // When I try to crown a non-existing king
        FortressUpdateDto body = FortressUpdateDto.builder().kingId(1).build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, "Fort-1");
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This dwarf doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void updateFortressCrownKingTest() {
        // Given a Fortress and a dwarf exist
        fortressRepository.save(FortressEntity.builder().name("Fort-1").build());
        dwarfRepository.save(DwarfEntity.builder().id(1).build());
        // When I crown the dwarf
        FortressUpdateDto body = FortressUpdateDto.builder().kingId(1).build();
        ResponseEntity<FortressDto> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), FortressDto.class, "Fort-1");
        // Then the dwarf is made king of that fortress
        FortressDto expectedResponse = FortressDto.builder().name("Fort-1").kingId(1).build();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void updateFortressCrownKingMigrateTest() {
        // Given 2 Fortresses and a dwarf exist in one of them
        FortressEntity fortress = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(FortressEntity.builder().name("Fort-2").build());
        dwarfRepository.save(DwarfEntity.builder().id(1).fortress(fortress).build());
        // When I crown the dwarf
        FortressUpdateDto body = FortressUpdateDto.builder().kingId(1).build();
        restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), FortressDto.class, "Fort-2");
        // Then the dwarf is migrated to that fortress
        Optional<DwarfEntity> dwarf = dwarfRepository.findById(1);
        assertTrue(dwarf.isPresent());
        assertEquals("Fort-2", dwarf.get().getFortress().getName());
    }

    @Test
    void updateFortressCrownKingAlreadyKingTest() {
        // Given 2 Fortresses and a dwarf exist in one of them
        FortressEntity fortress = FortressEntity.builder().name("Fort-1").build();
        fortressRepository.save(fortress);
        DwarfEntity dwarf = DwarfEntity.builder().id(1).fortress(fortress).build();
        fortress.setKing(dwarf);
        dwarfRepository.save(dwarf);
        fortressRepository.save(FortressEntity.builder().name("Fort-2").build());
        // When I crown the dwarf
        FortressUpdateDto body = FortressUpdateDto.builder().kingId(1).build();
        ResponseEntity<DefaultError> actualResponse = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(body), DefaultError.class, "Fort-1");
        // Then an error is returned with status 400
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("A king never abandons his fortress!", actualResponse.getBody().getMessage());
    }
}
