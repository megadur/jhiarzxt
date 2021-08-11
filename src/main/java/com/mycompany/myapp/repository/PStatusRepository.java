package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PStatusRepository extends JpaRepository<PStatus, Long> {}
