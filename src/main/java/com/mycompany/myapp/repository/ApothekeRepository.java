package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Apotheke;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Apotheke entity.
 */
@Repository
public interface ApothekeRepository extends JpaRepository<Apotheke, Long> {
    @Query(
        value = "select distinct apotheke from Apotheke apotheke left join fetch apotheke.rechenzentrums",
        countQuery = "select count(distinct apotheke) from Apotheke apotheke"
    )
    Page<Apotheke> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct apotheke from Apotheke apotheke left join fetch apotheke.rechenzentrums")
    List<Apotheke> findAllWithEagerRelationships();

    @Query("select apotheke from Apotheke apotheke left join fetch apotheke.rechenzentrums where apotheke.id =:id")
    Optional<Apotheke> findOneWithEagerRelationships(@Param("id") Long id);
}
