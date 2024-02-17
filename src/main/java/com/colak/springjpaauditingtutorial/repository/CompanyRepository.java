package com.colak.springjpaauditingtutorial.repository;

import com.colak.springjpaauditingtutorial.jpa.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends RevisionRepository<Company, Long, Long>,JpaRepository<Company, Long> {
}
