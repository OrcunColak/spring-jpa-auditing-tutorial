package com.colak.springjpaauditingtutorial.repository;

import com.colak.springjpaauditingtutorial.config.SpringSecurityAuditorAwareImpl;
import com.colak.springjpaauditingtutorial.jpa.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @Order(1)
    void testSave() throws InterruptedException {
        Company company = new Company();
        company.setName("supplier");

        Company savedCompany1 = companyRepository.saveAndFlush(company);

        // Auditor names
        // Created by is set by SpringSecurityAuditorAwareImpl object
        assertEquals(SpringSecurityAuditorAwareImpl.AUDITOR_NAME, savedCompany1.getCreatedBy());
        assertEquals(SpringSecurityAuditorAwareImpl.AUDITOR_NAME, savedCompany1.getLastModifiedBy());

        // @Temporal(TemporalType.TIMESTAMP) fields are set by Spring
        assertNotNull(savedCompany1.getCreatedDate());
        assertNotNull(savedCompany1.getLastModifiedDate());

        TimeUnit.SECONDS.sleep(2);

        // Update
        Company updateCompany = new Company();
        updateCompany.setId(savedCompany1.getId());
        updateCompany.setName("new supplier");
        // Save entity does not read not updated columns
        companyRepository.save(updateCompany);

        // So we need to read the updated entity again to compare with the first entity
        Company savedCompany2 = companyRepository.findById(savedCompany1.getId())
                .get();

        assertNotEquals(savedCompany1.getName(), savedCompany2.getName());

        // Auditor names
        assertEquals(savedCompany1.getCreatedBy(), savedCompany2.getCreatedBy());
        assertEquals(savedCompany1.getLastModifiedBy(), savedCompany2.getLastModifiedBy());

        // Temporal timestamps
        assertEquals(savedCompany1.getCreatedDate(), savedCompany2.getCreatedDate());
        assertNotEquals(savedCompany1.getLastModifiedDate(), savedCompany2.getLastModifiedDate());
    }

    @Test
    @Order(1)
    void testHistory() {
        Revisions<Long, Company> revisions = companyRepository.findRevisions(1L);
        assertNotNull(revisions);

        List<Company> history = revisions.get()
                .map(Revision::getEntity)
                .toList();

        log.info("History : {} ", history);

        companyRepository.findLastChangeRevision(1L)
                .ifPresentOrElse(lastRevision -> {
                            log.info("lastRevision : {} ", lastRevision.getEntity());
                        },
                        () -> fail("Test failed"));
    }
}
