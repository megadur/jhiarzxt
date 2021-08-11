package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Muster16Ver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Muster16Ver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Muster16VerRepository extends JpaRepository<Muster16Ver, Long> {}
