package com.colak.springtutorial.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "company")

// JPA Auditing
@EntityListeners(AuditingEntityListener.class)

// Envers
// @NotAudited : If you don't want the relationship to be audited mark it as org.hibernate.envers.NotAudited
@Audited // // This annotation indicates that this entity should be audited
// This is not necessary because we are setting table suffix from application-envers.properties
// @AuditTable("company_history")

// Lombok
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
public class Company extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}
