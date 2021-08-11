package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Rechenzentrum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rechenzentrum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RechenzentrumRepository extends JpaRepository<Rechenzentrum, Long> {}
