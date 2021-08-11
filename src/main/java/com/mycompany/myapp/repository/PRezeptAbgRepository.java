package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PRezeptAbg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PRezeptAbg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PRezeptAbgRepository extends JpaRepository<PRezeptAbg, Long> {}
