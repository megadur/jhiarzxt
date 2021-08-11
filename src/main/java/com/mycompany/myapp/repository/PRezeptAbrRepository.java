package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PRezeptAbr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PRezeptAbr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRezeptAbrRepository extends JpaRepository<PRezeptAbr, Long> {}
