package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PStatusInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PStatusInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PStatusInfoRepository extends JpaRepository<PStatusInfo, Long> {}
