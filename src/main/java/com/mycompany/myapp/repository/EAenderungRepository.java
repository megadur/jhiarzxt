package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EAenderung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EAenderung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EAenderungRepository extends JpaRepository<EAenderung, Long> {}
