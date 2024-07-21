package com.mountainhome.database.repositories;

import com.mountainhome.database.TestDataUtil;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkstationEntityTypeDaoImplIntegrationTests {
    private WorkstationTypeRepository underTest;

    @Autowired
    public WorkstationEntityTypeDaoImplIntegrationTests(WorkstationTypeRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void CreateReadWorkstationType() {
        WorkstationEntity workstationEntity = TestDataUtil.createTestWorkstationTypeByName("Still");
        underTest.save(workstationEntity);
        Optional<WorkstationEntity> result = underTest.findById(1);
        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(workstationEntity);
    }
}
