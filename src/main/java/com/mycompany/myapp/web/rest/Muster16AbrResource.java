package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Muster16Abr;
import com.mycompany.myapp.repository.Muster16AbrRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Muster16Abr}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Muster16AbrResource {

    private final Logger log = LoggerFactory.getLogger(Muster16AbrResource.class);

    private static final String ENTITY_NAME = "muster16Abr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Muster16AbrRepository muster16AbrRepository;

    public Muster16AbrResource(Muster16AbrRepository muster16AbrRepository) {
        this.muster16AbrRepository = muster16AbrRepository;
    }

    /**
     * {@code POST  /muster-16-abrs} : Create a new muster16Abr.
     *
     * @param muster16Abr the muster16Abr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new muster16Abr, or with status {@code 400 (Bad Request)} if the muster16Abr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muster-16-abrs")
    public ResponseEntity<Muster16Abr> createMuster16Abr(@RequestBody Muster16Abr muster16Abr) throws URISyntaxException {
        log.debug("REST request to save Muster16Abr : {}", muster16Abr);
        if (muster16Abr.getId() != null) {
            throw new BadRequestAlertException("A new muster16Abr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Muster16Abr result = muster16AbrRepository.save(muster16Abr);
        return ResponseEntity
            .created(new URI("/api/muster-16-abrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muster-16-abrs/:id} : Updates an existing muster16Abr.
     *
     * @param id the id of the muster16Abr to save.
     * @param muster16Abr the muster16Abr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Abr,
     * or with status {@code 400 (Bad Request)} if the muster16Abr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the muster16Abr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muster-16-abrs/{id}")
    public ResponseEntity<Muster16Abr> updateMuster16Abr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Abr muster16Abr
    ) throws URISyntaxException {
        log.debug("REST request to update Muster16Abr : {}, {}", id, muster16Abr);
        if (muster16Abr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Abr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16AbrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Muster16Abr result = muster16AbrRepository.save(muster16Abr);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Abr.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /muster-16-abrs/:id} : Partial updates given fields of an existing muster16Abr, field will ignore if it is null
     *
     * @param id the id of the muster16Abr to save.
     * @param muster16Abr the muster16Abr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16Abr,
     * or with status {@code 400 (Bad Request)} if the muster16Abr is not valid,
     * or with status {@code 404 (Not Found)} if the muster16Abr is not found,
     * or with status {@code 500 (Internal Server Error)} if the muster16Abr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/muster-16-abrs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Muster16Abr> partialUpdateMuster16Abr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16Abr muster16Abr
    ) throws URISyntaxException {
        log.debug("REST request to partial update Muster16Abr partially : {}, {}", id, muster16Abr);
        if (muster16Abr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16Abr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16AbrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Muster16Abr> result = muster16AbrRepository
            .findById(muster16Abr.getId())
            .map(
                existingMuster16Abr -> {
                    if (muster16Abr.getBetragRabA() != null) {
                        existingMuster16Abr.setBetragRabA(muster16Abr.getBetragRabA());
                    }
                    if (muster16Abr.getBetragRabH() != null) {
                        existingMuster16Abr.setBetragRabH(muster16Abr.getBetragRabH());
                    }
                    if (muster16Abr.getBetragApoAusz() != null) {
                        existingMuster16Abr.setBetragApoAusz(muster16Abr.getBetragApoAusz());
                    }

                    return existingMuster16Abr;
                }
            )
            .map(muster16AbrRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16Abr.getId().toString())
        );
    }

    /**
     * {@code GET  /muster-16-abrs} : get all the muster16Abrs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of muster16Abrs in body.
     */
    @GetMapping("/muster-16-abrs")
    public List<Muster16Abr> getAllMuster16Abrs(@RequestParam(required = false) String filter) {
        if ("mrezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all Muster16Abrs where mRezeptId is null");
            return StreamSupport
                .stream(muster16AbrRepository.findAll().spliterator(), false)
                .filter(muster16Abr -> muster16Abr.getMRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Muster16Abrs");
        return muster16AbrRepository.findAll();
    }

    /**
     * {@code GET  /muster-16-abrs/:id} : get the "id" muster16Abr.
     *
     * @param id the id of the muster16Abr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the muster16Abr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muster-16-abrs/{id}")
    public ResponseEntity<Muster16Abr> getMuster16Abr(@PathVariable Long id) {
        log.debug("REST request to get Muster16Abr : {}", id);
        Optional<Muster16Abr> muster16Abr = muster16AbrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(muster16Abr);
    }

    /**
     * {@code DELETE  /muster-16-abrs/:id} : delete the "id" muster16Abr.
     *
     * @param id the id of the muster16Abr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muster-16-abrs/{id}")
    public ResponseEntity<Void> deleteMuster16Abr(@PathVariable Long id) {
        log.debug("REST request to delete Muster16Abr : {}", id);
        muster16AbrRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
