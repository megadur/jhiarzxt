package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Muster16Abr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Muster16Abr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Muster16AbrRepository extends JpaRepository<Muster16Abr, Long> {}
