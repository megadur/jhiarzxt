package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Rechenzentrum;
import com.mycompany.myapp.repository.RechenzentrumRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Rechenzentrum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RechenzentrumResource {

    private final Logger log = LoggerFactory.getLogger(RechenzentrumResource.class);

    private static final String ENTITY_NAME = "rechenzentrum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RechenzentrumRepository rechenzentrumRepository;

    public RechenzentrumResource(RechenzentrumRepository rechenzentrumRepository) {
        this.rechenzentrumRepository = rechenzentrumRepository;
    }

    /**
     * {@code POST  /rechenzentrums} : Create a new rechenzentrum.
     *
     * @param rechenzentrum the rechenzentrum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rechenzentrum, or with status {@code 400 (Bad Request)} if the rechenzentrum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rechenzentrums")
    public ResponseEntity<Rechenzentrum> createRechenzentrum(@RequestBody Rechenzentrum rechenzentrum) throws URISyntaxException {
        log.debug("REST request to save Rechenzentrum : {}", rechenzentrum);
        if (rechenzentrum.getId() != null) {
            throw new BadRequestAlertException("A new rechenzentrum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rechenzentrum result = rechenzentrumRepository.save(rechenzentrum);
        return ResponseEntity
            .created(new URI("/api/rechenzentrums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rechenzentrums/:id} : Updates an existing rechenzentrum.
     *
     * @param id the id of the rechenzentrum to save.
     * @param rechenzentrum the rechenzentrum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechenzentrum,
     * or with status {@code 400 (Bad Request)} if the rechenzentrum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rechenzentrum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rechenzentrums/{id}")
    public ResponseEntity<Rechenzentrum> updateRechenzentrum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rechenzentrum rechenzentrum
    ) throws URISyntaxException {
        log.debug("REST request to update Rechenzentrum : {}, {}", id, rechenzentrum);
        if (rechenzentrum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechenzentrum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechenzentrumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rechenzentrum result = rechenzentrumRepository.save(rechenzentrum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rechenzentrum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rechenzentrums/:id} : Partial updates given fields of an existing rechenzentrum, field will ignore if it is null
     *
     * @param id the id of the rechenzentrum to save.
     * @param rechenzentrum the rechenzentrum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rechenzentrum,
     * or with status {@code 400 (Bad Request)} if the rechenzentrum is not valid,
     * or with status {@code 404 (Not Found)} if the rechenzentrum is not found,
     * or with status {@code 500 (Internal Server Error)} if the rechenzentrum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rechenzentrums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Rechenzentrum> partialUpdateRechenzentrum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rechenzentrum rechenzentrum
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rechenzentrum partially : {}, {}", id, rechenzentrum);
        if (rechenzentrum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rechenzentrum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rechenzentrumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rechenzentrum> result = rechenzentrumRepository
            .findById(rechenzentrum.getId())
            .map(
                existingRechenzentrum -> {
                    if (rechenzentrum.getiD() != null) {
                        existingRechenzentrum.setiD(rechenzentrum.getiD());
                    }
                    if (rechenzentrum.getName() != null) {
                        existingRechenzentrum.setName(rechenzentrum.getName());
                    }

                    return existingRechenzentrum;
                }
            )
            .map(rechenzentrumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rechenzentrum.getId().toString())
        );
    }

    /**
     * {@code GET  /rechenzentrums} : get all the rechenzentrums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rechenzentrums in body.
     */
    @GetMapping("/rechenzentrums")
    public List<Rechenzentrum> getAllRechenzentrums() {
        log.debug("REST request to get all Rechenzentrums");
        return rechenzentrumRepository.findAll();
    }

    /**
     * {@code GET  /rechenzentrums/:id} : get the "id" rechenzentrum.
     *
     * @param id the id of the rechenzentrum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rechenzentrum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rechenzentrums/{id}")
    public ResponseEntity<Rechenzentrum> getRechenzentrum(@PathVariable Long id) {
        log.debug("REST request to get Rechenzentrum : {}", id);
        Optional<Rechenzentrum> rechenzentrum = rechenzentrumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rechenzentrum);
    }

    /**
     * {@code DELETE  /rechenzentrums/:id} : delete the "id" rechenzentrum.
     *
     * @param id the id of the rechenzentrum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rechenzentrums/{id}")
    public ResponseEntity<Void> deleteRechenzentrum(@PathVariable Long id) {
        log.debug("REST request to delete Rechenzentrum : {}", id);
        rechenzentrumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
