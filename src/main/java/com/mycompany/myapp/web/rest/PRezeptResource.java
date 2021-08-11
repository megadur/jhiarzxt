package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PRezept;
import com.mycompany.myapp.repository.PRezeptRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PRezept}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PRezeptResource {

    private final Logger log = LoggerFactory.getLogger(PRezeptResource.class);

    private static final String ENTITY_NAME = "pRezept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PRezeptRepository pRezeptRepository;

    public PRezeptResource(PRezeptRepository pRezeptRepository) {
        this.pRezeptRepository = pRezeptRepository;
    }

    /**
     * {@code POST  /p-rezepts} : Create a new pRezept.
     *
     * @param pRezept the pRezept to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pRezept, or with status {@code 400 (Bad Request)} if the pRezept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-rezepts")
    public ResponseEntity<PRezept> createPRezept(@RequestBody PRezept pRezept) throws URISyntaxException {
        log.debug("REST request to save PRezept : {}", pRezept);
        if (pRezept.getId() != null) {
            throw new BadRequestAlertException("A new pRezept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PRezept result = pRezeptRepository.save(pRezept);
        return ResponseEntity
            .created(new URI("/api/p-rezepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-rezepts/:id} : Updates an existing pRezept.
     *
     * @param id the id of the pRezept to save.
     * @param pRezept the pRezept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezept,
     * or with status {@code 400 (Bad Request)} if the pRezept is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pRezept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-rezepts/{id}")
    public ResponseEntity<PRezept> updatePRezept(@PathVariable(value = "id", required = false) final Long id, @RequestBody PRezept pRezept)
        throws URISyntaxException {
        log.debug("REST request to update PRezept : {}, {}", id, pRezept);
        if (pRezept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PRezept result = pRezeptRepository.save(pRezept);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezept.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-rezepts/:id} : Partial updates given fields of an existing pRezept, field will ignore if it is null
     *
     * @param id the id of the pRezept to save.
     * @param pRezept the pRezept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezept,
     * or with status {@code 400 (Bad Request)} if the pRezept is not valid,
     * or with status {@code 404 (Not Found)} if the pRezept is not found,
     * or with status {@code 500 (Internal Server Error)} if the pRezept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-rezepts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PRezept> partialUpdatePRezept(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezept pRezept
    ) throws URISyntaxException {
        log.debug("REST request to partial update PRezept partially : {}, {}", id, pRezept);
        if (pRezept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PRezept> result = pRezeptRepository
            .findById(pRezept.getId())
            .map(
                existingPRezept -> {
                    if (pRezept.getpRezeptId() != null) {
                        existingPRezept.setpRezeptId(pRezept.getpRezeptId());
                    }
                    if (pRezept.getLieferdat() != null) {
                        existingPRezept.setLieferdat(pRezept.getLieferdat());
                    }
                    if (pRezept.getLieferungId() != null) {
                        existingPRezept.setLieferungId(pRezept.getLieferungId());
                    }
                    if (pRezept.getaPeriode() != null) {
                        existingPRezept.setaPeriode(pRezept.getaPeriode());
                    }
                    if (pRezept.getAbDatum() != null) {
                        existingPRezept.setAbDatum(pRezept.getAbDatum());
                    }

                    return existingPRezept;
                }
            )
            .map(pRezeptRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezept.getId().toString())
        );
    }

    /**
     * {@code GET  /p-rezepts} : get all the pRezepts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pRezepts in body.
     */
    @GetMapping("/p-rezepts")
    public List<PRezept> getAllPRezepts() {
        log.debug("REST request to get all PRezepts");
        return pRezeptRepository.findAll();
    }

    /**
     * {@code GET  /p-rezepts/:id} : get the "id" pRezept.
     *
     * @param id the id of the pRezept to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pRezept, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-rezepts/{id}")
    public ResponseEntity<PRezept> getPRezept(@PathVariable Long id) {
        log.debug("REST request to get PRezept : {}", id);
        Optional<PRezept> pRezept = pRezeptRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pRezept);
    }

    /**
     * {@code DELETE  /p-rezepts/:id} : delete the "id" pRezept.
     *
     * @param id the id of the pRezept to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-rezepts/{id}")
    public ResponseEntity<Void> deletePRezept(@PathVariable Long id) {
        log.debug("REST request to delete PRezept : {}", id);
        pRezeptRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
