package com.colak.springtutorial.repository;

import com.colak.springtutorial.jpa.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends RevisionRepository<Company, Long, Long>,JpaRepository<Company, Long> {
}
