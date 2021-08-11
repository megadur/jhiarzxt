package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ERezept;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ERezept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ERezeptRepository extends JpaRepository<ERezept, Long> {}
