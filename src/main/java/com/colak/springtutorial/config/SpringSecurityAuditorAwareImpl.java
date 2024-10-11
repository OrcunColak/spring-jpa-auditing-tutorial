package com.colak.springtutorial.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * To tell JPA about currently logged-in users, we will need to provide an implementation of AuditorAware and
 * override the getCurrentAuditor() method. And inside getCurrentAuditor(), we will need to fetch a currently logged-in user.
 */
public class SpringSecurityAuditorAwareImpl implements AuditorAware<String> {

    public static final String AUDITOR_NAME = "myauditor";
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(AUDITOR_NAME);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//        return Optional.of(authentication.getName());
    }
}
