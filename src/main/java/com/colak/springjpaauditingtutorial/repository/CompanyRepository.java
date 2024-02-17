package com.colak.springjpaauditingtutorial.repository;

import com.colak.springjpaauditingtutorial.jpa.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
