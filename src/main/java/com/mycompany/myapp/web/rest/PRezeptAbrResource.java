package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PRezeptAbr;
import com.mycompany.myapp.repository.PRezeptAbrRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PRezeptAbr}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PRezeptAbrResource {

    private final Logger log = LoggerFactory.getLogger(PRezeptAbrResource.class);

    private static final String ENTITY_NAME = "pRezeptAbr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PRezeptAbrRepository pRezeptAbrRepository;

    public PRezeptAbrResource(PRezeptAbrRepository pRezeptAbrRepository) {
        this.pRezeptAbrRepository = pRezeptAbrRepository;
    }

    /**
     * {@code POST  /p-rezept-abrs} : Create a new pRezeptAbr.
     *
     * @param pRezeptAbr the pRezeptAbr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pRezeptAbr, or with status {@code 400 (Bad Request)} if the pRezeptAbr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-rezept-abrs")
    public ResponseEntity<PRezeptAbr> createPRezeptAbr(@RequestBody PRezeptAbr pRezeptAbr) throws URISyntaxException {
        log.debug("REST request to save PRezeptAbr : {}", pRezeptAbr);
        if (pRezeptAbr.getId() != null) {
            throw new BadRequestAlertException("A new pRezeptAbr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PRezeptAbr result = pRezeptAbrRepository.save(pRezeptAbr);
        return ResponseEntity
            .created(new URI("/api/p-rezept-abrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-rezept-abrs/:id} : Updates an existing pRezeptAbr.
     *
     * @param id the id of the pRezeptAbr to save.
     * @param pRezeptAbr the pRezeptAbr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptAbr,
     * or with status {@code 400 (Bad Request)} if the pRezeptAbr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptAbr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-rezept-abrs/{id}")
    public ResponseEntity<PRezeptAbr> updatePRezeptAbr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptAbr pRezeptAbr
    ) throws URISyntaxException {
        log.debug("REST request to update PRezeptAbr : {}, {}", id, pRezeptAbr);
        if (pRezeptAbr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptAbr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptAbrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PRezeptAbr result = pRezeptAbrRepository.save(pRezeptAbr);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptAbr.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-rezept-abrs/:id} : Partial updates given fields of an existing pRezeptAbr, field will ignore if it is null
     *
     * @param id the id of the pRezeptAbr to save.
     * @param pRezeptAbr the pRezeptAbr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pRezeptAbr,
     * or with status {@code 400 (Bad Request)} if the pRezeptAbr is not valid,
     * or with status {@code 404 (Not Found)} if the pRezeptAbr is not found,
     * or with status {@code 500 (Internal Server Error)} if the pRezeptAbr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-rezept-abrs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PRezeptAbr> partialUpdatePRezeptAbr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PRezeptAbr pRezeptAbr
    ) throws URISyntaxException {
        log.debug("REST request to partial update PRezeptAbr partially : {}, {}", id, pRezeptAbr);
        if (pRezeptAbr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pRezeptAbr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pRezeptAbrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PRezeptAbr> result = pRezeptAbrRepository
            .findById(pRezeptAbr.getId())
            .map(
                existingPRezeptAbr -> {
                    if (pRezeptAbr.getpRezeptId() != null) {
                        existingPRezeptAbr.setpRezeptId(pRezeptAbr.getpRezeptId());
                    }
                    if (pRezeptAbr.getBetragRabA() != null) {
                        existingPRezeptAbr.setBetragRabA(pRezeptAbr.getBetragRabA());
                    }
                    if (pRezeptAbr.getBetragRabH() != null) {
                        existingPRezeptAbr.setBetragRabH(pRezeptAbr.getBetragRabH());
                    }
                    if (pRezeptAbr.getBetragApoAusz() != null) {
                        existingPRezeptAbr.setBetragApoAusz(pRezeptAbr.getBetragApoAusz());
                    }

                    return existingPRezeptAbr;
                }
            )
            .map(pRezeptAbrRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pRezeptAbr.getId().toString())
        );
    }

    /**
     * {@code GET  /p-rezept-abrs} : get all the pRezeptAbrs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pRezeptAbrs in body.
     */
    @GetMapping("/p-rezept-abrs")
    public List<PRezeptAbr> getAllPRezeptAbrs(@RequestParam(required = false) String filter) {
        if ("prezeptid-is-null".equals(filter)) {
            log.debug("REST request to get all PRezeptAbrs where pRezeptId is null");
            return StreamSupport
                .stream(pRezeptAbrRepository.findAll().spliterator(), false)
                .filter(pRezeptAbr -> pRezeptAbr.getPRezeptId() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PRezeptAbrs");
        return pRezeptAbrRepository.findAll();
    }

    /**
     * {@code GET  /p-rezept-abrs/:id} : get the "id" pRezeptAbr.
     *
     * @param id the id of the pRezeptAbr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pRezeptAbr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-rezept-abrs/{id}")
    public ResponseEntity<PRezeptAbr> getPRezeptAbr(@PathVariable Long id) {
        log.debug("REST request to get PRezeptAbr : {}", id);
        Optional<PRezeptAbr> pRezeptAbr = pRezeptAbrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pRezeptAbr);
    }

    /**
     * {@code DELETE  /p-rezept-abrs/:id} : delete the "id" pRezeptAbr.
     *
     * @param id the id of the pRezeptAbr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-rezept-abrs/{id}")
    public ResponseEntity<Void> deletePRezeptAbr(@PathVariable Long id) {
        log.debug("REST request to delete PRezeptAbr : {}", id);
        pRezeptAbrRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
