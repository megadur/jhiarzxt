package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PCharge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PChargeRepository extends JpaRepository<PCharge, Long> {}
