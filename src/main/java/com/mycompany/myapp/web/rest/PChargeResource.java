package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PCharge;
import com.mycompany.myapp.repository.PChargeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PCharge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PChargeResource {

    private final Logger log = LoggerFactory.getLogger(PChargeResource.class);

    private static final String ENTITY_NAME = "pCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PChargeRepository pChargeRepository;

    public PChargeResource(PChargeRepository pChargeRepository) {
        this.pChargeRepository = pChargeRepository;
    }

    /**
     * {@code POST  /p-charges} : Create a new pCharge.
     *
     * @param pCharge the pCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pCharge, or with status {@code 400 (Bad Request)} if the pCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-charges")
    public ResponseEntity<PCharge> createPCharge(@RequestBody PCharge pCharge) throws URISyntaxException {
        log.debug("REST request to save PCharge : {}", pCharge);
        if (pCharge.getId() != null) {
            throw new BadRequestAlertException("A new pCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PCharge result = pChargeRepository.save(pCharge);
        return ResponseEntity
            .created(new URI("/api/p-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-charges/:id} : Updates an existing pCharge.
     *
     * @param id the id of the pCharge to save.
     * @param pCharge the pCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pCharge,
     * or with status {@code 400 (Bad Request)} if the pCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-charges/{id}")
    public ResponseEntity<PCharge> updatePCharge(@PathVariable(value = "id", required = false) final Long id, @RequestBody PCharge pCharge)
        throws URISyntaxException {
        log.debug("REST request to update PCharge : {}, {}", id, pCharge);
        if (pCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PCharge result = pChargeRepository.save(pCharge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pCharge.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-charges/:id} : Partial updates given fields of an existing pCharge, field will ignore if it is null
     *
     * @param id the id of the pCharge to save.
     * @param pCharge the pCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pCharge,
     * or with status {@code 400 (Bad Request)} if the pCharge is not valid,
     * or with status {@code 404 (Not Found)} if the pCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the pCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-charges/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PCharge> partialUpdatePCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PCharge pCharge
    ) throws URISyntaxException {
        log.debug("REST request to partial update PCharge partially : {}, {}", id, pCharge);
        if (pCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PCharge> result = pChargeRepository
            .findById(pCharge.getId())
            .map(
                existingPCharge -> {
                    if (pCharge.getpChargeId() != null) {
                        existingPCharge.setpChargeId(pCharge.getpChargeId());
                    }
                    if (pCharge.getpRezeptId() != null) {
                        existingPCharge.setpRezeptId(pCharge.getpRezeptId());
                    }
                    if (pCharge.getAnzahlApplikationen() != null) {
                        existingPCharge.setAnzahlApplikationen(pCharge.getAnzahlApplikationen());
                    }
                    if (pCharge.getApoIk() != null) {
                        existingPCharge.setApoIk(pCharge.getApoIk());
                    }
                    if (pCharge.getChargenNr() != null) {
                        existingPCharge.setChargenNr(pCharge.getChargenNr());
                    }
                    if (pCharge.getHerstellerNr() != null) {
                        existingPCharge.setHerstellerNr(pCharge.getHerstellerNr());
                    }
                    if (pCharge.getHerstellerSchluessel() != null) {
                        existingPCharge.setHerstellerSchluessel(pCharge.getHerstellerSchluessel());
                    }
                    if (pCharge.getHerstellungsDatum() != null) {
                        existingPCharge.setHerstellungsDatum(pCharge.getHerstellungsDatum());
                    }

                    return existingPCharge;
                }
            )
            .map(pChargeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pCharge.getId().toString())
        );
    }

    /**
     * {@code GET  /p-charges} : get all the pCharges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pCharges in body.
     */
    @GetMapping("/p-charges")
    public List<PCharge> getAllPCharges() {
        log.debug("REST request to get all PCharges");
        return pChargeRepository.findAll();
    }

    /**
     * {@code GET  /p-charges/:id} : get the "id" pCharge.
     *
     * @param id the id of the pCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-charges/{id}")
    public ResponseEntity<PCharge> getPCharge(@PathVariable Long id) {
        log.debug("REST request to get PCharge : {}", id);
        Optional<PCharge> pCharge = pChargeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pCharge);
    }

    /**
     * {@code DELETE  /p-charges/:id} : delete the "id" pCharge.
     *
     * @param id the id of the pCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-charges/{id}")
    public ResponseEntity<Void> deletePCharge(@PathVariable Long id) {
        log.debug("REST request to delete PCharge : {}", id);
        pChargeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
