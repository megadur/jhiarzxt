package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PStatus;
import com.mycompany.myapp.repository.PStatusRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PStatusResource {

    private final Logger log = LoggerFactory.getLogger(PStatusResource.class);

    private static final String ENTITY_NAME = "pStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PStatusRepository pStatusRepository;

    public PStatusResource(PStatusRepository pStatusRepository) {
        this.pStatusRepository = pStatusRepository;
    }

    /**
     * {@code POST  /p-statuses} : Create a new pStatus.
     *
     * @param pStatus the pStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pStatus, or with status {@code 400 (Bad Request)} if the pStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-statuses")
    public ResponseEntity<PStatus> createPStatus(@RequestBody PStatus pStatus) throws URISyntaxException {
        log.debug("REST request to save PStatus : {}", pStatus);
        if (pStatus.getId() != null) {
            throw new BadRequestAlertException("A new pStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PStatus result = pStatusRepository.save(pStatus);
        return ResponseEntity
            .created(new URI("/api/p-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-statuses/:id} : Updates an existing pStatus.
     *
     * @param id the id of the pStatus to save.
     * @param pStatus the pStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pStatus,
     * or with status {@code 400 (Bad Request)} if the pStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-statuses/{id}")
    public ResponseEntity<PStatus> updatePStatus(@PathVariable(value = "id", required = false) final Long id, @RequestBody PStatus pStatus)
        throws URISyntaxException {
        log.debug("REST request to update PStatus : {}, {}", id, pStatus);
        if (pStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PStatus result = pStatusRepository.save(pStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-statuses/:id} : Partial updates given fields of an existing pStatus, field will ignore if it is null
     *
     * @param id the id of the pStatus to save.
     * @param pStatus the pStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pStatus,
     * or with status {@code 400 (Bad Request)} if the pStatus is not valid,
     * or with status {@code 404 (Not Found)} if the pStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the pStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PStatus> partialUpdatePStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PStatus pStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update PStatus partially : {}, {}", id, pStatus);
        if (pStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PStatus> result = pStatusRepository
            .findById(pStatus.getId())
            .map(
                existingPStatus -> {
                    if (pStatus.getWert() != null) {
                        existingPStatus.setWert(pStatus.getWert());
                    }
                    if (pStatus.getBeschreibung() != null) {
                        existingPStatus.setBeschreibung(pStatus.getBeschreibung());
                    }

                    return existingPStatus;
                }
            )
            .map(pStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /p-statuses} : get all the pStatuses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pStatuses in body.
     */
    @GetMapping("/p-statuses")
    public List<PStatus> getAllPStatuses(@RequestParam(required = false) String filter) {
        if ("pstatusid-is-null".equals(filter)) {
            log.debug("REST request to get all PStatuss where pStatusId is null");
            return StreamSupport
                .stream(pStatusRepository.findAll().spliterator(), false)
                .filter(pStatus -> pStatus.getPStatusId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PStatuses");
        return pStatusRepository.findAll();
    }

    /**
     * {@code GET  /p-statuses/:id} : get the "id" pStatus.
     *
     * @param id the id of the pStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-statuses/{id}")
    public ResponseEntity<PStatus> getPStatus(@PathVariable Long id) {
        log.debug("REST request to get PStatus : {}", id);
        Optional<PStatus> pStatus = pStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pStatus);
    }

    /**
     * {@code DELETE  /p-statuses/:id} : delete the "id" pStatus.
     *
     * @param id the id of the pStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-statuses/{id}")
    public ResponseEntity<Void> deletePStatus(@PathVariable Long id) {
        log.debug("REST request to delete PStatus : {}", id);
        pStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
