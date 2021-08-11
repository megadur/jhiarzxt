package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.M16Status;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the M16Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface M16StatusRepository extends JpaRepository<M16Status, Long> {}
