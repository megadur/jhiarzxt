package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Lieferung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Lieferung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LieferungRepository extends JpaRepository<Lieferung, Long> {}
