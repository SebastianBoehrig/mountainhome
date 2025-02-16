package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.dto.JobInputProductDto;
import com.mountainhome.database.helper.DefaultError;
import com.mountainhome.database.helper.RestTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Slf4j
class WorkstationTypeGetTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void getWorkstationTypeNamesTest() {
        // When I get the existing workstationTypes
        ResponseEntity<String[]> response = restTemplate.getForEntity("http://localhost:" + port + "/workstation", String[].class);
        // Then they match up with the values from the sql file
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Farm", response.getBody()[0]);
    }

    @Test
    void getAllWorkstationTypeJobsBadWorkstationTest() {
        // When I get the jobs for a non-existing workstationType
        ResponseEntity<DefaultError> actualResponse = restTemplate.getForEntity("http://localhost:" + port + "/workstation/{workstation_name}", DefaultError.class,"Bull");
        // Then all available jobs of that workstationType are returned

        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertEquals("This type of Workstation doesn't exist!", actualResponse.getBody().getMessage());
    }

    @Test
    void getAllWorkstationTypeJobsTest() {
        // When I get the jobs for a specific workstationType
        ResponseEntity<JobDto[]> actualResponse = restTemplate.getForEntity("http://localhost:" + port + "/workstation/{workstation_name}", JobDto[].class,"Farm");
        // Then all available jobs of that workstationType are returned
        JobInputProductDto product = JobInputProductDto.builder().resourceName("Egg").amount(12).build();
        JobDto[] expectedResponse = {JobDto.builder().id(1).name("Collect Eggs").products(List.of(product)).inputs(List.of()).build()};

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertThat(actualResponse.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
