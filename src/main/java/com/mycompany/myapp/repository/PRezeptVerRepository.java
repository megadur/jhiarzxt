package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PRezeptVer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PRezeptVer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRezeptVerRepository extends JpaRepository<PRezeptVer, Long> {}
