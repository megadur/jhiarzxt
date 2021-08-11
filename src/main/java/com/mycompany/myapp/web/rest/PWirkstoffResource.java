package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PWirkstoff;
import com.mycompany.myapp.repository.PWirkstoffRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PWirkstoff}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PWirkstoffResource {

    private final Logger log = LoggerFactory.getLogger(PWirkstoffResource.class);

    private static final String ENTITY_NAME = "pWirkstoff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PWirkstoffRepository pWirkstoffRepository;

    public PWirkstoffResource(PWirkstoffRepository pWirkstoffRepository) {
        this.pWirkstoffRepository = pWirkstoffRepository;
    }

    /**
     * {@code POST  /p-wirkstoffs} : Create a new pWirkstoff.
     *
     * @param pWirkstoff the pWirkstoff to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pWirkstoff, or with status {@code 400 (Bad Request)} if the pWirkstoff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/p-wirkstoffs")
    public ResponseEntity<PWirkstoff> createPWirkstoff(@RequestBody PWirkstoff pWirkstoff) throws URISyntaxException {
        log.debug("REST request to save PWirkstoff : {}", pWirkstoff);
        if (pWirkstoff.getId() != null) {
            throw new BadRequestAlertException("A new pWirkstoff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PWirkstoff result = pWirkstoffRepository.save(pWirkstoff);
        return ResponseEntity
            .created(new URI("/api/p-wirkstoffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /p-wirkstoffs/:id} : Updates an existing pWirkstoff.
     *
     * @param id the id of the pWirkstoff to save.
     * @param pWirkstoff the pWirkstoff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pWirkstoff,
     * or with status {@code 400 (Bad Request)} if the pWirkstoff is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pWirkstoff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/p-wirkstoffs/{id}")
    public ResponseEntity<PWirkstoff> updatePWirkstoff(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PWirkstoff pWirkstoff
    ) throws URISyntaxException {
        log.debug("REST request to update PWirkstoff : {}, {}", id, pWirkstoff);
        if (pWirkstoff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pWirkstoff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pWirkstoffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PWirkstoff result = pWirkstoffRepository.save(pWirkstoff);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pWirkstoff.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /p-wirkstoffs/:id} : Partial updates given fields of an existing pWirkstoff, field will ignore if it is null
     *
     * @param id the id of the pWirkstoff to save.
     * @param pWirkstoff the pWirkstoff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pWirkstoff,
     * or with status {@code 400 (Bad Request)} if the pWirkstoff is not valid,
     * or with status {@code 404 (Not Found)} if the pWirkstoff is not found,
     * or with status {@code 500 (Internal Server Error)} if the pWirkstoff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/p-wirkstoffs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PWirkstoff> partialUpdatePWirkstoff(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PWirkstoff pWirkstoff
    ) throws URISyntaxException {
        log.debug("REST request to partial update PWirkstoff partially : {}, {}", id, pWirkstoff);
        if (pWirkstoff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pWirkstoff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pWirkstoffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PWirkstoff> result = pWirkstoffRepository
            .findById(pWirkstoff.getId())
            .map(
                existingPWirkstoff -> {
                    if (pWirkstoff.getpWirkstoffId() != null) {
                        existingPWirkstoff.setpWirkstoffId(pWirkstoff.getpWirkstoffId());
                    }
                    if (pWirkstoff.getpChargeId() != null) {
                        existingPWirkstoff.setpChargeId(pWirkstoff.getpChargeId());
                    }
                    if (pWirkstoff.getApoIk() != null) {
                        existingPWirkstoff.setApoIk(pWirkstoff.getApoIk());
                    }
                    if (pWirkstoff.getFaktor() != null) {
                        existingPWirkstoff.setFaktor(pWirkstoff.getFaktor());
                    }
                    if (pWirkstoff.getFaktorKennzeichen() != null) {
                        existingPWirkstoff.setFaktorKennzeichen(pWirkstoff.getFaktorKennzeichen());
                    }
                    if (pWirkstoff.getNotiz() != null) {
                        existingPWirkstoff.setNotiz(pWirkstoff.getNotiz());
                    }
                    if (pWirkstoff.getpPosNr() != null) {
                        existingPWirkstoff.setpPosNr(pWirkstoff.getpPosNr());
                    }
                    if (pWirkstoff.getPreisKennzeichen() != null) {
                        existingPWirkstoff.setPreisKennzeichen(pWirkstoff.getPreisKennzeichen());
                    }
                    if (pWirkstoff.getPzn() != null) {
                        existingPWirkstoff.setPzn(pWirkstoff.getPzn());
                    }
                    if (pWirkstoff.getTaxe() != null) {
                        existingPWirkstoff.setTaxe(pWirkstoff.getTaxe());
                    }
                    if (pWirkstoff.getWirkstoffName() != null) {
                        existingPWirkstoff.setWirkstoffName(pWirkstoff.getWirkstoffName());
                    }

                    return existingPWirkstoff;
                }
            )
            .map(pWirkstoffRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pWirkstoff.getId().toString())
        );
    }

    /**
     * {@code GET  /p-wirkstoffs} : get all the pWirkstoffs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pWirkstoffs in body.
     */
    @GetMapping("/p-wirkstoffs")
    public List<PWirkstoff> getAllPWirkstoffs() {
        log.debug("REST request to get all PWirkstoffs");
        return pWirkstoffRepository.findAll();
    }

    /**
     * {@code GET  /p-wirkstoffs/:id} : get the "id" pWirkstoff.
     *
     * @param id the id of the pWirkstoff to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pWirkstoff, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/p-wirkstoffs/{id}")
    public ResponseEntity<PWirkstoff> getPWirkstoff(@PathVariable Long id) {
        log.debug("REST request to get PWirkstoff : {}", id);
        Optional<PWirkstoff> pWirkstoff = pWirkstoffRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pWirkstoff);
    }

    /**
     * {@code DELETE  /p-wirkstoffs/:id} : delete the "id" pWirkstoff.
     *
     * @param id the id of the pWirkstoff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/p-wirkstoffs/{id}")
    public ResponseEntity<Void> deletePWirkstoff(@PathVariable Long id) {
        log.debug("REST request to delete PWirkstoff : {}", id);
        pWirkstoffRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
