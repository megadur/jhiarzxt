package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Apotheke;
import com.mycompany.myapp.repository.ApothekeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Apotheke}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ApothekeResource {

    private final Logger log = LoggerFactory.getLogger(ApothekeResource.class);

    private static final String ENTITY_NAME = "apotheke";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApothekeRepository apothekeRepository;

    public ApothekeResource(ApothekeRepository apothekeRepository) {
        this.apothekeRepository = apothekeRepository;
    }

    /**
     * {@code POST  /apothekes} : Create a new apotheke.
     *
     * @param apotheke the apotheke to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apotheke, or with status {@code 400 (Bad Request)} if the apotheke has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apothekes")
    public ResponseEntity<Apotheke> createApotheke(@RequestBody Apotheke apotheke) throws URISyntaxException {
        log.debug("REST request to save Apotheke : {}", apotheke);
        if (apotheke.getId() != null) {
            throw new BadRequestAlertException("A new apotheke cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Apotheke result = apothekeRepository.save(apotheke);
        return ResponseEntity
            .created(new URI("/api/apothekes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /apothekes/:id} : Updates an existing apotheke.
     *
     * @param id the id of the apotheke to save.
     * @param apotheke the apotheke to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apotheke,
     * or with status {@code 400 (Bad Request)} if the apotheke is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apotheke couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apothekes/{id}")
    public ResponseEntity<Apotheke> updateApotheke(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apotheke apotheke
    ) throws URISyntaxException {
        log.debug("REST request to update Apotheke : {}, {}", id, apotheke);
        if (apotheke.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apotheke.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apothekeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Apotheke result = apothekeRepository.save(apotheke);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apotheke.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /apothekes/:id} : Partial updates given fields of an existing apotheke, field will ignore if it is null
     *
     * @param id the id of the apotheke to save.
     * @param apotheke the apotheke to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apotheke,
     * or with status {@code 400 (Bad Request)} if the apotheke is not valid,
     * or with status {@code 404 (Not Found)} if the apotheke is not found,
     * or with status {@code 500 (Internal Server Error)} if the apotheke couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/apothekes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Apotheke> partialUpdateApotheke(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apotheke apotheke
    ) throws URISyntaxException {
        log.debug("REST request to partial update Apotheke partially : {}, {}", id, apotheke);
        if (apotheke.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apotheke.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apothekeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apotheke> result = apothekeRepository
            .findById(apotheke.getId())
            .map(
                existingApotheke -> {
                    if (apotheke.getiK() != null) {
                        existingApotheke.setiK(apotheke.getiK());
                    }
                    if (apotheke.getInhaber() != null) {
                        existingApotheke.setInhaber(apotheke.getInhaber());
                    }
                    if (apotheke.getCountryCode() != null) {
                        existingApotheke.setCountryCode(apotheke.getCountryCode());
                    }
                    if (apotheke.getPlz() != null) {
                        existingApotheke.setPlz(apotheke.getPlz());
                    }
                    if (apotheke.getOrt() != null) {
                        existingApotheke.setOrt(apotheke.getOrt());
                    }
                    if (apotheke.getStr() != null) {
                        existingApotheke.setStr(apotheke.getStr());
                    }
                    if (apotheke.getHausNr() != null) {
                        existingApotheke.setHausNr(apotheke.getHausNr());
                    }
                    if (apotheke.getAddrZusatz() != null) {
                        existingApotheke.setAddrZusatz(apotheke.getAddrZusatz());
                    }

                    return existingApotheke;
                }
            )
            .map(apothekeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apotheke.getId().toString())
        );
    }

    /**
     * {@code GET  /apothekes} : get all the apothekes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apothekes in body.
     */
    @GetMapping("/apothekes")
    public List<Apotheke> getAllApothekes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Apothekes");
        return apothekeRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /apothekes/:id} : get the "id" apotheke.
     *
     * @param id the id of the apotheke to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apotheke, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apothekes/{id}")
    public ResponseEntity<Apotheke> getApotheke(@PathVariable Long id) {
        log.debug("REST request to get Apotheke : {}", id);
        Optional<Apotheke> apotheke = apothekeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(apotheke);
    }

    /**
     * {@code DELETE  /apothekes/:id} : delete the "id" apotheke.
     *
     * @param id the id of the apotheke to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apothekes/{id}")
    public ResponseEntity<Void> deleteApotheke(@PathVariable Long id) {
        log.debug("REST request to delete Apotheke : {}", id);
        apothekeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
