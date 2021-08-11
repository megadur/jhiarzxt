package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Muster16Abg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Muster16Abg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Muster16AbgRepository extends JpaRepository<Muster16Abg, Long> {}
