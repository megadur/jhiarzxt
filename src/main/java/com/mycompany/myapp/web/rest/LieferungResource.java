package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Lieferung;
import com.mycompany.myapp.repository.LieferungRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Lieferung}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LieferungResource {

    private final Logger log = LoggerFactory.getLogger(LieferungResource.class);

    private static final String ENTITY_NAME = "lieferung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LieferungRepository lieferungRepository;

    public LieferungResource(LieferungRepository lieferungRepository) {
        this.lieferungRepository = lieferungRepository;
    }

    /**
     * {@code POST  /lieferungs} : Create a new lieferung.
     *
     * @param lieferung the lieferung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lieferung, or with status {@code 400 (Bad Request)} if the lieferung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lieferungs")
    public ResponseEntity<Lieferung> createLieferung(@RequestBody Lieferung lieferung) throws URISyntaxException {
        log.debug("REST request to save Lieferung : {}", lieferung);
        if (lieferung.getId() != null) {
            throw new BadRequestAlertException("A new lieferung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lieferung result = lieferungRepository.save(lieferung);
        return ResponseEntity
            .created(new URI("/api/lieferungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lieferungs/:id} : Updates an existing lieferung.
     *
     * @param id the id of the lieferung to save.
     * @param lieferung the lieferung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lieferung,
     * or with status {@code 400 (Bad Request)} if the lieferung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lieferung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lieferungs/{id}")
    public ResponseEntity<Lieferung> updateLieferung(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Lieferung lieferung
    ) throws URISyntaxException {
        log.debug("REST request to update Lieferung : {}, {}", id, lieferung);
        if (lieferung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lieferung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lieferungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Lieferung result = lieferungRepository.save(lieferung);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lieferung.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lieferungs/:id} : Partial updates given fields of an existing lieferung, field will ignore if it is null
     *
     * @param id the id of the lieferung to save.
     * @param lieferung the lieferung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lieferung,
     * or with status {@code 400 (Bad Request)} if the lieferung is not valid,
     * or with status {@code 404 (Not Found)} if the lieferung is not found,
     * or with status {@code 500 (Internal Server Error)} if the lieferung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lieferungs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Lieferung> partialUpdateLieferung(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Lieferung lieferung
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lieferung partially : {}, {}", id, lieferung);
        if (lieferung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lieferung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lieferungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lieferung> result = lieferungRepository
            .findById(lieferung.getId())
            .map(
                existingLieferung -> {
                    if (lieferung.getiD() != null) {
                        existingLieferung.setiD(lieferung.getiD());
                    }
                    if (lieferung.getDatum() != null) {
                        existingLieferung.setDatum(lieferung.getDatum());
                    }
                    if (lieferung.getApoIk() != null) {
                        existingLieferung.setApoIk(lieferung.getApoIk());
                    }

                    return existingLieferung;
                }
            )
            .map(lieferungRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lieferung.getId().toString())
        );
    }

    /**
     * {@code GET  /lieferungs} : get all the lieferungs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lieferungs in body.
     */
    @GetMapping("/lieferungs")
    public List<Lieferung> getAllLieferungs() {
        log.debug("REST request to get all Lieferungs");
        return lieferungRepository.findAll();
    }

    /**
     * {@code GET  /lieferungs/:id} : get the "id" lieferung.
     *
     * @param id the id of the lieferung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lieferung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lieferungs/{id}")
    public ResponseEntity<Lieferung> getLieferung(@PathVariable Long id) {
        log.debug("REST request to get Lieferung : {}", id);
        Optional<Lieferung> lieferung = lieferungRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lieferung);
    }

    /**
     * {@code DELETE  /lieferungs/:id} : delete the "id" lieferung.
     *
     * @param id the id of the lieferung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lieferungs/{id}")
    public ResponseEntity<Void> deleteLieferung(@PathVariable Long id) {
        log.debug("REST request to delete Lieferung : {}", id);
        lieferungRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
