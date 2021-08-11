package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EAenderung;
import com.mycompany.myapp.repository.EAenderungRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EAenderung}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EAenderungResource {

    private final Logger log = LoggerFactory.getLogger(EAenderungResource.class);

    private static final String ENTITY_NAME = "eAenderung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EAenderungRepository eAenderungRepository;

    public EAenderungResource(EAenderungRepository eAenderungRepository) {
        this.eAenderungRepository = eAenderungRepository;
    }

    /**
     * {@code POST  /e-aenderungs} : Create a new eAenderung.
     *
     * @param eAenderung the eAenderung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eAenderung, or with status {@code 400 (Bad Request)} if the eAenderung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/e-aenderungs")
    public ResponseEntity<EAenderung> createEAenderung(@RequestBody EAenderung eAenderung) throws URISyntaxException {
        log.debug("REST request to save EAenderung : {}", eAenderung);
        if (eAenderung.getId() != null) {
            throw new BadRequestAlertException("A new eAenderung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EAenderung result = eAenderungRepository.save(eAenderung);
        return ResponseEntity
            .created(new URI("/api/e-aenderungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /e-aenderungs/:id} : Updates an existing eAenderung.
     *
     * @param id the id of the eAenderung to save.
     * @param eAenderung the eAenderung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eAenderung,
     * or with status {@code 400 (Bad Request)} if the eAenderung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eAenderung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/e-aenderungs/{id}")
    public ResponseEntity<EAenderung> updateEAenderung(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EAenderung eAenderung
    ) throws URISyntaxException {
        log.debug("REST request to update EAenderung : {}, {}", id, eAenderung);
        if (eAenderung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eAenderung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eAenderungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EAenderung result = eAenderungRepository.save(eAenderung);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eAenderung.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /e-aenderungs/:id} : Partial updates given fields of an existing eAenderung, field will ignore if it is null
     *
     * @param id the id of the eAenderung to save.
     * @param eAenderung the eAenderung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eAenderung,
     * or with status {@code 400 (Bad Request)} if the eAenderung is not valid,
     * or with status {@code 404 (Not Found)} if the eAenderung is not found,
     * or with status {@code 500 (Internal Server Error)} if the eAenderung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/e-aenderungs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EAenderung> partialUpdateEAenderung(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EAenderung eAenderung
    ) throws URISyntaxException {
        log.debug("REST request to partial update EAenderung partially : {}, {}", id, eAenderung);
        if (eAenderung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eAenderung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eAenderungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EAenderung> result = eAenderungRepository
            .findById(eAenderung.getId())
            .map(
                existingEAenderung -> {
                    if (eAenderung.getSchluessel() != null) {
                        existingEAenderung.setSchluessel(eAenderung.getSchluessel());
                    }
                    if (eAenderung.getDokuRezept() != null) {
                        existingEAenderung.setDokuRezept(eAenderung.getDokuRezept());
                    }
                    if (eAenderung.getDokuArzt() != null) {
                        existingEAenderung.setDokuArzt(eAenderung.getDokuArzt());
                    }
                    if (eAenderung.getDatum() != null) {
                        existingEAenderung.setDatum(eAenderung.getDatum());
                    }

                    return existingEAenderung;
                }
            )
            .map(eAenderungRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eAenderung.getId().toString())
        );
    }

    /**
     * {@code GET  /e-aenderungs} : get all the eAenderungs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eAenderungs in body.
     */
    @GetMapping("/e-aenderungs")
    public List<EAenderung> getAllEAenderungs() {
        log.debug("REST request to get all EAenderungs");
        return eAenderungRepository.findAll();
    }

    /**
     * {@code GET  /e-aenderungs/:id} : get the "id" eAenderung.
     *
     * @param id the id of the eAenderung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eAenderung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/e-aenderungs/{id}")
    public ResponseEntity<EAenderung> getEAenderung(@PathVariable Long id) {
        log.debug("REST request to get EAenderung : {}", id);
        Optional<EAenderung> eAenderung = eAenderungRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eAenderung);
    }

    /**
     * {@code DELETE  /e-aenderungs/:id} : delete the "id" eAenderung.
     *
     * @param id the id of the eAenderung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/e-aenderungs/{id}")
    public ResponseEntity<Void> deleteEAenderung(@PathVariable Long id) {
        log.debug("REST request to delete EAenderung : {}", id);
        eAenderungRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
