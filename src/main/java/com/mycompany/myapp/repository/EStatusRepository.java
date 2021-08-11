package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EStatusRepository extends JpaRepository<EStatus, Long> {}
