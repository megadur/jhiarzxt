package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PWirkstoff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PWirkstoff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PWirkstoffRepository extends JpaRepository<PWirkstoff, Long> {}
