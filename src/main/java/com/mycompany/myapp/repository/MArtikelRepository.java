package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MArtikel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MArtikel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MArtikelRepository extends JpaRepository<MArtikel, Long> {}
