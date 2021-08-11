package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EStatus;
import com.mycompany.myapp.repository.EStatusRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EStatusResource {

    private final Logger log = LoggerFactory.getLogger(EStatusResource.class);

    private static final String ENTITY_NAME = "eStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EStatusRepository eStatusRepository;

    public EStatusResource(EStatusRepository eStatusRepository) {
        this.eStatusRepository = eStatusRepository;
    }

    /**
     * {@code POST  /e-statuses} : Create a new eStatus.
     *
     * @param eStatus the eStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eStatus, or with status {@code 400 (Bad Request)} if the eStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/e-statuses")
    public ResponseEntity<EStatus> createEStatus(@RequestBody EStatus eStatus) throws URISyntaxException {
        log.debug("REST request to save EStatus : {}", eStatus);
        if (eStatus.getId() != null) {
            throw new BadRequestAlertException("A new eStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EStatus result = eStatusRepository.save(eStatus);
        return ResponseEntity
            .created(new URI("/api/e-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /e-statuses/:id} : Updates an existing eStatus.
     *
     * @param id the id of the eStatus to save.
     * @param eStatus the eStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eStatus,
     * or with status {@code 400 (Bad Request)} if the eStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/e-statuses/{id}")
    public ResponseEntity<EStatus> updateEStatus(@PathVariable(value = "id", required = false) final Long id, @RequestBody EStatus eStatus)
        throws URISyntaxException {
        log.debug("REST request to update EStatus : {}, {}", id, eStatus);
        if (eStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EStatus result = eStatusRepository.save(eStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /e-statuses/:id} : Partial updates given fields of an existing eStatus, field will ignore if it is null
     *
     * @param id the id of the eStatus to save.
     * @param eStatus the eStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eStatus,
     * or with status {@code 400 (Bad Request)} if the eStatus is not valid,
     * or with status {@code 404 (Not Found)} if the eStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the eStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/e-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EStatus> partialUpdateEStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EStatus eStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update EStatus partially : {}, {}", id, eStatus);
        if (eStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EStatus> result = eStatusRepository
            .findById(eStatus.getId())
            .map(
                existingEStatus -> {
                    if (eStatus.getWert() != null) {
                        existingEStatus.setWert(eStatus.getWert());
                    }
                    if (eStatus.getBeschreibung() != null) {
                        existingEStatus.setBeschreibung(eStatus.getBeschreibung());
                    }

                    return existingEStatus;
                }
            )
            .map(eStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /e-statuses} : get all the eStatuses.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eStatuses in body.
     */
    @GetMapping("/e-statuses")
    public List<EStatus> getAllEStatuses(@RequestParam(required = false) String filter) {
        if ("estatusid-is-null".equals(filter)) {
            log.debug("REST request to get all EStatuss where eStatusId is null");
            return StreamSupport
                .stream(eStatusRepository.findAll().spliterator(), false)
                .filter(eStatus -> eStatus.getEStatusId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all EStatuses");
        return eStatusRepository.findAll();
    }

    /**
     * {@code GET  /e-statuses/:id} : get the "id" eStatus.
     *
     * @param id the id of the eStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/e-statuses/{id}")
    public ResponseEntity<EStatus> getEStatus(@PathVariable Long id) {
        log.debug("REST request to get EStatus : {}", id);
        Optional<EStatus> eStatus = eStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eStatus);
    }

    /**
     * {@code DELETE  /e-statuses/:id} : delete the "id" eStatus.
     *
     * @param id the id of the eStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/e-statuses/{id}")
    public ResponseEntity<Void> deleteEStatus(@PathVariable Long id) {
        log.debug("REST request to delete EStatus : {}", id);
        eStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
