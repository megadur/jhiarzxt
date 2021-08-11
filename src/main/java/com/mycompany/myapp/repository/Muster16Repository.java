package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Muster16;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Muster16 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Muster16Repository extends JpaRepository<Muster16, Long> {}
