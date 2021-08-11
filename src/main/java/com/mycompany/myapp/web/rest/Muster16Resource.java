package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Muster16;
import com.mycompany.myapp.repository.Muster16Repository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Muster16}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class Muster16Resource {

    private final Logger log = LoggerFactory.getLogger(Muster16Resource.class);

    private static final String ENTITY_NAME = "muster16";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Muster16Repository muster16Repository;

    public Muster16Resource(Muster16Repository muster16Repository) {
        this.muster16Repository = muster16Repository;
    }

    /**
     * {@code POST  /muster-16-s} : Create a new muster16.
     *
     * @param muster16 the muster16 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new muster16, or with status {@code 400 (Bad Request)} if the muster16 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muster-16-s")
    public ResponseEntity<Muster16> createMuster16(@RequestBody Muster16 muster16) throws URISyntaxException {
        log.debug("REST request to save Muster16 : {}", muster16);
        if (muster16.getId() != null) {
            throw new BadRequestAlertException("A new muster16 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Muster16 result = muster16Repository.save(muster16);
        return ResponseEntity
            .created(new URI("/api/muster-16-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muster-16-s/:id} : Updates an existing muster16.
     *
     * @param id the id of the muster16 to save.
     * @param muster16 the muster16 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16,
     * or with status {@code 400 (Bad Request)} if the muster16 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the muster16 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muster-16-s/{id}")
    public ResponseEntity<Muster16> updateMuster16(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16 muster16
    ) throws URISyntaxException {
        log.debug("REST request to update Muster16 : {}, {}", id, muster16);
        if (muster16.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Muster16 result = muster16Repository.save(muster16);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /muster-16-s/:id} : Partial updates given fields of an existing muster16, field will ignore if it is null
     *
     * @param id the id of the muster16 to save.
     * @param muster16 the muster16 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muster16,
     * or with status {@code 400 (Bad Request)} if the muster16 is not valid,
     * or with status {@code 404 (Not Found)} if the muster16 is not found,
     * or with status {@code 500 (Internal Server Error)} if the muster16 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/muster-16-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Muster16> partialUpdateMuster16(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muster16 muster16
    ) throws URISyntaxException {
        log.debug("REST request to partial update Muster16 partially : {}, {}", id, muster16);
        if (muster16.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muster16.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muster16Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Muster16> result = muster16Repository
            .findById(muster16.getId())
            .map(
                existingMuster16 -> {
                    if (muster16.getmRezeptId() != null) {
                        existingMuster16.setmRezeptId(muster16.getmRezeptId());
                    }
                    if (muster16.getLieferungId() != null) {
                        existingMuster16.setLieferungId(muster16.getLieferungId());
                    }
                    if (muster16.getm16Status() != null) {
                        existingMuster16.setm16Status(muster16.getm16Status());
                    }
                    if (muster16.getmMuster16Id() != null) {
                        existingMuster16.setmMuster16Id(muster16.getmMuster16Id());
                    }
                    if (muster16.getApoIkSend() != null) {
                        existingMuster16.setApoIkSend(muster16.getApoIkSend());
                    }
                    if (muster16.getApoIkSnd() != null) {
                        existingMuster16.setApoIkSnd(muster16.getApoIkSnd());
                    }

                    return existingMuster16;
                }
            )
            .map(muster16Repository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, muster16.getId().toString())
        );
    }

    /**
     * {@code GET  /muster-16-s} : get all the muster16s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of muster16s in body.
     */
    @GetMapping("/muster-16-s")
    public List<Muster16> getAllMuster16s() {
        log.debug("REST request to get all Muster16s");
        return muster16Repository.findAll();
    }

    /**
     * {@code GET  /muster-16-s/:id} : get the "id" muster16.
     *
     * @param id the id of the muster16 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the muster16, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muster-16-s/{id}")
    public ResponseEntity<Muster16> getMuster16(@PathVariable Long id) {
        log.debug("REST request to get Muster16 : {}", id);
        Optional<Muster16> muster16 = muster16Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(muster16);
    }

    /**
     * {@code DELETE  /muster-16-s/:id} : delete the "id" muster16.
     *
     * @param id the id of the muster16 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muster-16-s/{id}")
    public ResponseEntity<Void> deleteMuster16(@PathVariable Long id) {
        log.debug("REST request to delete Muster16 : {}", id);
        muster16Repository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
