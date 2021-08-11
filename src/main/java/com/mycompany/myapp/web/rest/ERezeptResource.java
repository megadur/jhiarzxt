package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ERezept;
import com.mycompany.myapp.repository.ERezeptRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ERezept}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ERezeptResource {

    private final Logger log = LoggerFactory.getLogger(ERezeptResource.class);

    private static final String ENTITY_NAME = "eRezept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ERezeptRepository eRezeptRepository;

    public ERezeptResource(ERezeptRepository eRezeptRepository) {
        this.eRezeptRepository = eRezeptRepository;
    }

    /**
     * {@code POST  /e-rezepts} : Create a new eRezept.
     *
     * @param eRezept the eRezept to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eRezept, or with status {@code 400 (Bad Request)} if the eRezept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/e-rezepts")
    public ResponseEntity<ERezept> createERezept(@RequestBody ERezept eRezept) throws URISyntaxException {
        log.debug("REST request to save ERezept : {}", eRezept);
        if (eRezept.getId() != null) {
            throw new BadRequestAlertException("A new eRezept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ERezept result = eRezeptRepository.save(eRezept);
        return ResponseEntity
            .created(new URI("/api/e-rezepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /e-rezepts/:id} : Updates an existing eRezept.
     *
     * @param id the id of the eRezept to save.
     * @param eRezept the eRezept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eRezept,
     * or with status {@code 400 (Bad Request)} if the eRezept is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eRezept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/e-rezepts/{id}")
    public ResponseEntity<ERezept> updateERezept(@PathVariable(value = "id", required = false) final Long id, @RequestBody ERezept eRezept)
        throws URISyntaxException {
        log.debug("REST request to update ERezept : {}, {}", id, eRezept);
        if (eRezept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eRezept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eRezeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ERezept result = eRezeptRepository.save(eRezept);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eRezept.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /e-rezepts/:id} : Partial updates given fields of an existing eRezept, field will ignore if it is null
     *
     * @param id the id of the eRezept to save.
     * @param eRezept the eRezept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eRezept,
     * or with status {@code 400 (Bad Request)} if the eRezept is not valid,
     * or with status {@code 404 (Not Found)} if the eRezept is not found,
     * or with status {@code 500 (Internal Server Error)} if the eRezept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/e-rezepts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ERezept> partialUpdateERezept(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ERezept eRezept
    ) throws URISyntaxException {
        log.debug("REST request to partial update ERezept partially : {}, {}", id, eRezept);
        if (eRezept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eRezept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eRezeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ERezept> result = eRezeptRepository
            .findById(eRezept.getId())
            .map(
                existingERezept -> {
                    if (eRezept.getiD() != null) {
                        existingERezept.setiD(eRezept.getiD());
                    }
                    if (eRezept.getDokVer() != null) {
                        existingERezept.setDokVer(eRezept.getDokVer());
                    }
                    if (eRezept.getAbgInfo() != null) {
                        existingERezept.setAbgInfo(eRezept.getAbgInfo());
                    }
                    if (eRezept.getAbgDatum() != null) {
                        existingERezept.setAbgDatum(eRezept.getAbgDatum());
                    }
                    if (eRezept.getAbgGesZuzahl() != null) {
                        existingERezept.setAbgGesZuzahl(eRezept.getAbgGesZuzahl());
                    }
                    if (eRezept.getAbgGesBrutto() != null) {
                        existingERezept.setAbgGesBrutto(eRezept.getAbgGesBrutto());
                    }
                    if (eRezept.getAbgVertragskz() != null) {
                        existingERezept.setAbgVertragskz(eRezept.getAbgVertragskz());
                    }

                    return existingERezept;
                }
            )
            .map(eRezeptRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eRezept.getId().toString())
        );
    }

    /**
     * {@code GET  /e-rezepts} : get all the eRezepts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eRezepts in body.
     */
    @GetMapping("/e-rezepts")
    public List<ERezept> getAllERezepts() {
        log.debug("REST request to get all ERezepts");
        return eRezeptRepository.findAll();
    }

    /**
     * {@code GET  /e-rezepts/:id} : get the "id" eRezept.
     *
     * @param id the id of the eRezept to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eRezept, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/e-rezepts/{id}")
    public ResponseEntity<ERezept> getERezept(@PathVariable Long id) {
        log.debug("REST request to get ERezept : {}", id);
        Optional<ERezept> eRezept = eRezeptRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eRezept);
    }

    /**
     * {@code DELETE  /e-rezepts/:id} : delete the "id" eRezept.
     *
     * @param id the id of the eRezept to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/e-rezepts/{id}")
    public ResponseEntity<Void> deleteERezept(@PathVariable Long id) {
        log.debug("REST request to delete ERezept : {}", id);
        eRezeptRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
